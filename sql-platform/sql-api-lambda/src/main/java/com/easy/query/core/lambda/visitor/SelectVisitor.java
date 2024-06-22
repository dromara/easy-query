package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.api.lambda.crud.read.group.IAggregation;
import com.easy.query.api.lambda.crud.read.group.IGroup;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.easy.query.core.lambda.util.ExpressionUtil.isGroupKey;
import static com.easy.query.core.lambda.util.ExpressionUtil.isVoid;
import static com.easy.query.core.lambda.util.SqlUtil.fieldName;

public class SelectVisitor extends BaseVisitor
{
    private String temp = "";
    private int parIndex=0;
    private final QueryData queryData;
    private ParameterExpression curParameter;

    public SelectVisitor(List<ParameterExpression> parameters, QueryData queryData)
    {
        super(parameters, queryData.getDbType());
        this.queryData = queryData;
    }

    public int getParIndex()
    {
        return parIndex;
    }

    @Override
    public void visit(NewExpression newExpression)
    {
        BlockExpression classBody = newExpression.getClassBody();
        if (classBody == null) return;
        List<Expression> expressions = classBody.getExpressions();
        for (int i = 0; i < expressions.size(); i++)
        {
            Expression expression = expressions.get(i);
            if (expression.getKind() == Kind.Variable)
            {
                VariableExpression variable = (VariableExpression) expression;
                visit(variable.getInit());
                String name = variable.getParameter().getName();
                if (!name.equals(temp))
                {
                    data.append(" AS ").append(name);
                }
                if (i < expressions.size() - 1)
                {
                    data.append(",");
                }
            }
        }
    }

    @Override
    public void visit(LambdaExpression<?> lambda)
    {
        List<ParameterExpression> parameters = lambda.getParameters();
        SelectVisitor selectVisitor = new SelectVisitor(parameters, queryData);
        selectVisitor.mesIndex = mesIndex;
        lambda.getBody().accept(selectVisitor);
        data.append(selectVisitor.getData());
        sqlValue.addAll(selectVisitor.getSqlValue());
        mesIndex = selectVisitor.mesIndex;
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
                temp = methodName + "()";
            }
            else if (parameters.contains(parameter) && !isVoid(methodCall.getMethod().getReturnType()))
            {
                int index = parameters.indexOf(parameter);
                String fieldName = fieldName(methodCall.getMethod());
                putField(index, fieldName);
                temp = fieldName;
            }
            else if (curParameter == parameter)
            {
                for (Expression arg : methodCall.getArgs())
                {
                    visit(arg);
                }
                String name = fieldName(methodCall.getMethod());
                if (!name.equals(temp))
                {
                    data.append(" AS ").append(name);
                }
                data.append(",");
            }
            else
            {
                throw new IllegalExpressionException(methodCall);
            }
        }
        else
        {
            methodCallVisitor(methodCall);
            temp = "";
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
                    temp = putGroupValue(groupExtData);
                }
                else
                {
                    int index = parameters.indexOf(parameter);
                    String fieldName = fieldName(field);
                    putField(index, fieldName);
                    temp = fieldName;
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
            temp = putGroupValue(groupExtData);
        }
        else
        {
            tryPutExprValue(fieldSelect);
        }
    }

    @Override
    public void visit(VariableExpression variableExpression)
    {
        curParameter = variableExpression.getParameter();
    }

    @Override
    public void visit(ParameterExpression parameter)
    {
        if (parameters.contains(parameter))
        {
            parIndex = parameters.indexOf(parameter);
        }
    }

    @Override
    public void visit(ConstantExpression constant)
    {
        addValue(constant.getValue());
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        addValue(reference.getValue());
    }

    @Override
    public void visit(ReturnExpression returnExpression)
    {
        int index = data.length() - 1;
        if (data.charAt(index) == ',')
        {
            data.deleteCharAt(index);
        }
    }

    private void addValue(Object value)
    {
        if (value == null)
        {
            data.append("NULL");
        }
        else
        {
            data.append(indexBlock());
            sqlValue.add(new SqlValue(value));
        }
    }

    private String putGroupValue(GroupExtData groupExtData)
    {
        String input = groupExtData.exprData.toString();
        // 正则表达式匹配 {}
        Pattern pattern = Pattern.compile("\\{}");
        Matcher matcher = pattern.matcher(input);
        // 使用StringBuilder来构建新的字符串
        StringBuilder sb = new StringBuilder(input.length());
        // 遍历输入字符串
        int prevEnd = 0; // 上一个匹配的结束位置
        while (matcher.find())
        {
            // 将从上一个匹配结束到当前匹配开始的部分添加到StringBuilder中
            sb.append(input.substring(prevEnd, matcher.start()));
            // 替换 {} 为 {index}
            sb.append(indexBlock());
            // 更新上一个匹配的结束位置为当前匹配的结束位置
            prevEnd = matcher.end();
        }
        // 如果输入字符串的末尾有未匹配的部分，将其添加到StringBuilder中
        if (prevEnd < input.length())
        {
            sb.append(input.substring(prevEnd));
        }

        data.append(sb);
        sqlValue.addAll(groupExtData.sqlValues);
        return sb.toString();
    }
}
