package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.api.lambda.sqlext.SqlTypes;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import com.easy.query.core.lambda.util.ParamMatcher;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.easy.query.core.lambda.util.ExpressionUtil.*;
import static com.easy.query.core.lambda.util.SqlUtil.*;

public class BaseVisitor extends DeepFindVisitor
{
    protected final List<ParameterExpression> parameters;
    protected final DbType dbType;
    protected final StringBuilder data = new StringBuilder();
    protected final List<SqlValue> sqlValue = new ArrayList<>();
    protected int mesIndex = 0;

    public BaseVisitor(List<ParameterExpression> parameters, DbType dbType)
    {
        this.parameters = parameters;
        this.dbType = dbType;
    }

    public String getData()
    {
        return data.toString();
    }

    public List<SqlValue> getSqlValue()
    {
        return sqlValue;
    }

    protected void matchSqlFunctions(MethodCallExpression methodCall,StringBuilder sb)
    {
        Method callMethod = methodCall.getMethod();
        SqlFunctions.Ext[] exts = callMethod.getAnnotationsByType(SqlFunctions.Ext.class);
        // 没有Ext注解的直接使用函数名
        if (exts.length == 0)
        {
            sb.append(callMethod.getName()).append("(");
            List<Expression> args = methodCall.getArgs();
            for (int i = 0; i < args.size(); i++)
            {
                Expression arg = args.get(i);
                visit(arg);
                if (i < args.size() - 1) sb.append(",");
            }
            sb.append(")");
        }
        else
        {
            String function = getTargetSqlFuncExt(exts).function();
            tryReplace(methodCall, function, sb);
        }
    }

    protected void matchSqlTypes(MethodCallExpression methodCall,StringBuilder sb)
    {
        Method callMethod = methodCall.getMethod();
        SqlTypes.Ext[] exts = callMethod.getAnnotationsByType(SqlTypes.Ext.class);

        if (exts.length == 0)
        {
            sb.append(callMethod.getName());
            if (!methodCall.getArgs().isEmpty())
            {
                sb.append("(");
                List<Expression> args = methodCall.getArgs();
                for (int i = 0; i < args.size(); i++)
                {
                    Expression arg = args.get(i);
                    visit(arg);
                    if (i < args.size() - 1) sb.append(",");
                }
                sb.append(")");
            }
        }
        else
        {
            String type = getSqlTypeExtByMethod(exts).type();
            tryReplace(methodCall, type, sb);
        }
    }

    protected void tryReplace(MethodCallExpression methodCall, String string, StringBuilder sqlSegment)
    {
        if (methodCall.getArgs().isEmpty())
        {
            sqlSegment.append(string);
        }
        else if (string.contains("{}"))
        {
            String[] splitFunc = string.split("\\{}");
            List<Expression> args = methodCall.getArgs();
            for (int i = 0; i < splitFunc.length; i++)
            {
                sqlSegment.append(splitFunc[i]);
                // 可变参数情况
                if (i == splitFunc.length - 2
                        && args.size() >= splitFunc.length)
                {
                    while (i < args.size())
                    {
                        visit(args.get(i));
                        if (i < args.size() - 1) sqlSegment.append(",");
                        i++;
                    }
                    sqlSegment.append(splitFunc[splitFunc.length - 1]);
                }
                // 正常情况
                else if (i < args.size()) visit(args.get(i));
            }
        }
        else if (string.contains("{") && string.contains("}"))
        {
            List<Expression> args = methodCall.getArgs();
            List<Parameter> methodParameters = Arrays.stream(methodCall.getMethod().getParameters()).collect(Collectors.toList());
            ParamMatcher match = match(string);
            List<String> functions = match.remainder;
            List<String> params = match.bracesContent;
            for (int i = 0; i < functions.size(); i++)
            {
                sqlSegment.append(functions.get(i));
                if (i < params.size())
                {
                    String param = params.get(i);
                    Parameter targetParam;
                    int index;
                    if (param.chars().allMatch(s -> Character.isDigit(s)))
                    {
                        //index形式
                        index = Integer.parseInt(param);
                        targetParam = methodParameters.get(index);
                    }
                    else
                    {
                        //arg名称形式
                        targetParam = methodParameters.stream().filter(f -> f.getName().equals(param)).findFirst().get();
                        index = methodParameters.indexOf(targetParam);
                    }

                    // 如果是可变参数
                    if (targetParam.isVarArgs())
                    {
                        while (index < args.size())
                        {
                            visit(args.get(index));
                            if (index < args.size() - 1) sqlSegment.append(",");
                            index++;
                        }
                    }
                    // 正常情况
                    else
                    {
                        visit(args.get(index));
                    }
                }
            }
        }
        else
        {
            throw new RuntimeException(methodCall.toString());
        }
    }

    protected SqlTypes.Ext getSqlTypeExtByMethod(SqlTypes.Ext[] exts)
    {
        List<SqlTypes.Ext> extList = Arrays.stream(exts).filter(f -> f.dbType() == dbType).collect(Collectors.toList());
        if (extList.isEmpty())
        {
            throw new RuntimeException("当前数据库类型:" + dbType + "\n没有找到对应数据库类型对应的注解\n" + Arrays.toString(exts));
        }
        else if (extList.size() > 1)
        {
            throw new RuntimeException("当前数据库类型:" + dbType + "\n找到了多个对应数据库类型对应的注解" + Arrays.toString(exts));
        }
        return extList.get(0);
    }

    protected SqlFunctions.Ext getTargetSqlFuncExt(SqlFunctions.Ext[] exts)
    {
        List<SqlFunctions.Ext> extList = Arrays.stream(exts).filter(f -> f.dbType() == dbType).collect(Collectors.toList());
        if (extList.isEmpty())
        {
            throw new RuntimeException("当前数据库类型:" + dbType + "\n没有找到对应数据库类型对应的注解\n" + Arrays.toString(exts));
        }
        else if (extList.size() > 1)
        {
            throw new RuntimeException("当前数据库类型:" + dbType + "\n找到了多个对应数据库类型对应的注解" + Arrays.toString(exts));
        }
        return extList.get(0);
    }

    protected String indexBlock()
    {
        return "{" + mesIndex++ + "}";
    }

    protected void cleanIndexBlock()
    {
        mesIndex = 0;
    }

    protected void putValue(Object value)
    {
        data.append(indexBlock());
        sqlValue.add(new SqlValue(value));
    }

    protected void putField(int index, String fieldName)
    {
        data.append(indexBlock());
        sqlValue.add(new SqlValue(SqlValue.Type.property, index, fieldName));
    }

    protected void tryPutExprValue(MethodCallExpression methodCall)
    {
        if (hasParameter(methodCall) || isVoid(methodCall.getMethod().getReturnType()))
        {
            throw new IllegalExpressionException(methodCall);
        }
        putValue(methodCall.getValue());
    }

    protected void tryPutExprValue(FieldSelectExpression fieldSelect)
    {
        if (hasParameter(fieldSelect) || isVoid(fieldSelect.getField().getType()))
        {
            throw new IllegalExpressionException(fieldSelect);
        }
        putValue(fieldSelect.getValue());
    }

    protected void methodCallVisitor(MethodCallExpression methodCall)
    {
        Method callMethod = methodCall.getMethod();
        Class<?> declaringClass = callMethod.getDeclaringClass();
        if (isEquals(callMethod))
        {
            visit(methodCall.getExpr());
            data.append(" ").append(toSqlOp(OperatorType.EQ)).append(" ");
            for (Expression arg : methodCall.getArgs())
            {
                visit(arg);
            }
        }
        else if (SqlFunctions.class.isAssignableFrom(declaringClass))
        {
            visit(methodCall.getExpr());
            matchSqlFunctions(methodCall,data);
        }
        else if (SqlTypes.class.isAssignableFrom(declaringClass))
        {
            visit(methodCall.getExpr());
            matchSqlTypes(methodCall,data);
        }
        else if (Iterable.class.isAssignableFrom(declaringClass))
        {
            Iterable<?> iterable = (Iterable<?>) methodCall.getExpr().getValue();
            Iterator<?> iterator = iterable.iterator();
            for (Expression arg : methodCall.getArgs())
            {
                visit(arg);
            }
            data.append(" IN ");
            data.append("(");
            for (Object o : iterable)
            {
                data.append(indexBlock());
                sqlValue.add(new SqlValue(o));
                if (iterator.hasNext())
                {
                    data.append(",");
                }
            }
            data.append(")");
        }
        else if (String.class.isAssignableFrom(declaringClass))
        {
            switch (callMethod.getName())
            {
                case "contains":
                    visit(methodCall.getExpr());
                {
                    data.append(" LIKE ")
                            .append("CONCAT('%',");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(",'%') ");
                }
                break;
                case "startsWith":
                    visit(methodCall.getExpr());
                {
                    data.append(" LIKE ").append("CONCAT(");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(",'%') ");
                }
                break;
                case "endsWith":
                    visit(methodCall.getExpr());
                {
                    data.append(" LIKE ").append("CONCAT('%',");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(") ");
                }
                break;
                default:
                    tryPutExprValue(methodCall);
            }

        }
        else if (BigDecimal.class.isAssignableFrom(declaringClass) || BigInteger.class.isAssignableFrom(declaringClass))
        {
            switch (callMethod.getName())
            {
                case "add":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.PLUS)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "subtract":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.MINUS)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "multiply":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.MUL)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "divide":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.DIV)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "remainder":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.MOD)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                default:
                    tryPutExprValue(methodCall);
            }
        }
//            else if (Date.class.isAssignableFrom(declaringClass))
//            {
//                switch (callMethod.getName())
//                {
//                    case "after":
//                        visit(methodCall.getExpr());
//                    {
//                        data.append(" ").append(toSqlOp(OperatorType.GT)).append(" ");
//                        for (Expression arg : methodCall.getArgs())
//                        {
//                            visit(arg);
//                        }
//                        break;
//                    }
//                    case "before":
//                        visit(methodCall.getExpr());
//                    {
//                        data.append(" ").append(toSqlOp(OperatorType.LT)).append(" ");
//                        for (Expression arg : methodCall.getArgs())
//                        {
//                            visit(arg);
//                        }
//                        break;
//                    }
//                    default:
//                        tryPutExprValue(methodCall);
//                }
//            }
        else if (Temporal.class.isAssignableFrom(declaringClass))
        {
            switch (callMethod.getName())
            {
                case "now":
                    visit(methodCall.getExpr());
                {
                    if (LocalDateTime.class.isAssignableFrom(declaringClass))
                    {
                        data.append("NOW()");
                    }
                    else if (LocalDate.class.isAssignableFrom(declaringClass))
                    {
                        data.append("CURDATE()");
                    }
                    break;
                }
                case "plus":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.PLUS)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "minus":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.MINUS)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "isAfter":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.GT)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "isBefore":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.LT)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "isEqual":
                    visit(methodCall.getExpr());
                {
                    data.append(" ").append(toSqlOp(OperatorType.EQ)).append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    break;
                }
                case "of":
                {
                    if (LocalTime.class.isAssignableFrom(declaringClass))
                    {
                        data.append("MAKETIME(");
                        List<Expression> args = methodCall.getArgs();
                        for (int i = 0; i < args.size(); i++)
                        {
                            Expression arg = args.get(i);
                            visit(arg);
                            if (i < args.size() - 1) data.append(",");
                        }
                        data.append(")");
                    }
                    break;
                }
                case "ofYearDay":
                    visit(methodCall.getExpr());
                {
                    if (LocalDate.class.isAssignableFrom(declaringClass))
                    {
                        data.append("MAKEDATE(");
                        List<Expression> args = methodCall.getArgs();
                        for (int i = 0; i < args.size(); i++)
                        {
                            Expression arg = args.get(i);
                            visit(arg);
                            if (i < args.size() - 1) data.append(",");
                        }
                        data.append(")");
                    }
                    break;
                }
                default:
                    tryPutExprValue(methodCall);
            }
        }
        else if (TemporalAmount.class.isAssignableFrom(declaringClass))
        {
            switch (callMethod.getName())
            {
                case "ofYears":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("YEAR");
                    break;
                }
                case "ofMonths":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("MONTH");
                    break;
                }
                case "ofWeeks":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("WEEK");
                    break;
                }
                case "ofDays":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("DAY");
                    break;
                }
                case "ofHours":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("HOUR");
                    break;
                }
                case "ofMinutes":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("MINUTE");
                    break;
                }
                case "ofSeconds":
                    visit(methodCall.getExpr());
                {
                    data.append("INTERVAL").append(" ");
                    for (Expression arg : methodCall.getArgs())
                    {
                        visit(arg);
                    }
                    data.append(" ").append("SECOND");
                    break;
                }
                default:
                    tryPutExprValue(methodCall);
            }
        }
        else
        {
            // 最后推断为值
            tryPutExprValue(methodCall);
        }
    }
}
