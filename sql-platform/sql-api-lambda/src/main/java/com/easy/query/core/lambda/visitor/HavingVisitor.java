package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.crud.read.IAggregation;
import com.easy.query.api.lambda.crud.read.IGroup;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.easy.query.core.lambda.util.ExpressionUtil.*;
import static com.easy.query.core.lambda.util.SqlUtil.*;


public class HavingVisitor extends BaseVisitor
{

    private final QueryData queryData;

    public HavingVisitor(List<ParameterExpression> parameters, QueryData queryData)
    {
        super(parameters,queryData.getDbType());
        this.queryData = queryData;
    }

    @Override
    public void visit(LambdaExpression<?> lambda)
    {
        List<ParameterExpression> parameters = lambda.getParameters();
        HavingVisitor havingVisitor = new HavingVisitor(parameters, queryData);
        havingVisitor.mesIndex = mesIndex;
        lambda.getBody().accept(havingVisitor);
        data.append(havingVisitor.getData());
        sqlValue.addAll(havingVisitor.getSqlValue());
        mesIndex = havingVisitor.mesIndex;
    }

    @Override
    public void visit(ParensExpression parensExpression)
    {
        data.append("(");
        visit(parensExpression.getExpr());
        data.append(")");
    }

    @Override
    public void visit(UnaryExpression unary)
    {
        data.append(toSqlOp(unary.getOperatorType()));
        Expression operand = unary.getOperand();
        String sqlOp = toSqlOp(unary.getOperatorType());
        data.append(operand.getKind() == Kind.Parens ? sqlOp : sqlOp + "(");
        visit(operand);
        if (operand.getKind() != Kind.Parens)
        {
            data.append(")");
        }
    }

    @Override
    public void visit(BinaryExpression binary)
    {
        visit(binary.getLeft());
        data.append(" ").append(toSqlOp(binary.getOperatorType())).append(" ");
        visit(binary.getRight());
    }

    @Override
    public void visit(ConstantExpression constant)
    {
        putValue(constant.getValue());
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        putValue(reference.getValue());
    }

    @Override
    public void visit(MethodCallExpression methodCall)
    {
        if (methodCall.getExpr().getKind() == Kind.Parameter)
        {
            ParameterExpression parameter = (ParameterExpression) methodCall.getExpr();
            if (IAggregation.class.isAssignableFrom(methodCall.getMethod().getDeclaringClass()))
            {
                Method method = methodCall.getMethod();
                List<Expression> args = methodCall.getArgs();
                String methodName = method.getName();
                data.append(methodName).append("(");
                if (args.isEmpty())
                {
                    data.append("*");
                }
                else
                {
                    for (Expression arg : args)
                    {
                        visit(arg);
                    }
                }
                data.append(")");
            }
            else if (parameters.contains(parameter) && !isVoid(methodCall.getMethod().getReturnType()))
            {
                int index = parameters.indexOf(parameter);
                putField(index, fieldName(methodCall.getMethod()));
            }
            else
            {
                throw new IllegalExpressionException(methodCall);
            }
        }
        else
        {
            methodCallVisitor(methodCall);
        }
    }

    @Override
    public void visit(FieldSelectExpression fieldSelect)
    {
        if (fieldSelect.getExpr().getKind() == Kind.Parameter)
        {
            ParameterExpression parameter = (ParameterExpression) fieldSelect.getExpr();
            Field field = fieldSelect.getField();
            if (parameters.contains(parameter))
            {
                if (IGroup.class.isAssignableFrom(field.getDeclaringClass())
                        && field.getName().equals("key"))
                {
                    GroupExtData groupExtData = queryData.getGroupExtDataMap().get("key");
                    putGroupValue(groupExtData);
                }
                else
                {
                    int index = parameters.indexOf(parameter);
                    String fieldName = fieldName(field);
                    putField(index, fieldName);
                }
            }
            else
            {
                throw new IllegalExpressionException(fieldSelect);
            }
        }
        else if (isGroupKey(parameters, fieldSelect.getExpr()))
        {
            Map<String, GroupExtData> gmap = queryData.getGroupExtDataMap();
            String fieldName = fieldName(fieldSelect.getField());
            GroupExtData groupExtData = gmap.get(fieldName);
            putGroupValue(groupExtData);
        }
        else
        {
            tryPutExprValue(fieldSelect);
        }
    }

    @Override
    public void visit(ConditionalExpression conditional)
    {
        Expression condition = conditional.getCondition();
        Expression truePart = conditional.getTruePart();
        Expression falsePart = conditional.getFalsePart();
        Object value = condition.getValue();
        if (value != null)
        {
            if ((boolean) value)
            {
                visit(truePart);
            }
            else
            {
                visit(falsePart);
            }
        }
        else
        {
            data.append("IF(");
            visit(condition);
            data.append(",");
            visit(truePart);
            data.append(",");
            visit(falsePart);
            data.append(")");
        }
    }

//    private void appendIfStringOrNull(Object value)
//    {
//        if (value == null)
//        {
//            data.append("NULL");
//        }
//        else
//        {
//            data.append("?");
//            if (value instanceof String)
//            {
//                if (nowIsContains)
//                {
//                    sqlValue.add(new SqlValue("%" + value + "%"));
//                }
//                else if (nowIsStartsWith)
//                {
//                    sqlValue.add(new SqlValue(value + "%"));
//                }
//                else if (nowIsEndsWith)
//                {
//                    sqlValue.add(new SqlValue("%" + value));
//                }
//                else
//                {
//                    sqlValue.add(new SqlValue(value));
//                }
//            }
//            else
//            {
//                sqlValue.add(new SqlValue(value));
//            }
//        }
//    }

    private void putGroupValue(GroupExtData groupExtData)
    {
        String input = groupExtData.exprData.toString();
        // 正则表达式匹配 {}
        Pattern pattern = Pattern.compile("\\{}");
        Matcher matcher = pattern.matcher(input);
        // 使用StringBuilder来构建新的字符串
        StringBuilder sb = new StringBuilder(input.length());
        // 遍历输入字符串
        int prevEnd = 0; // 上一个匹配的结束位置
        while (matcher.find()) {
            // 将从上一个匹配结束到当前匹配开始的部分添加到StringBuilder中
            sb.append(input.substring(prevEnd, matcher.start()));
            // 替换 {} 为 {index}
            sb.append(indexBlock());
            // 更新上一个匹配的结束位置为当前匹配的结束位置
            prevEnd = matcher.end();
        }
        // 如果输入字符串的末尾有未匹配的部分，将其添加到StringBuilder中
        if (prevEnd < input.length()) {
            sb.append(input.substring(prevEnd));
        }
        data.append(sb);
        sqlValue.addAll(groupExtData.sqlValues);
    }
}
