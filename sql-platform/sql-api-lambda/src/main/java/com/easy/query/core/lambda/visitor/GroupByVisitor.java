package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.api.lambda.sqlext.SqlTypes;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import com.easy.query.core.lambda.util.ParamMatcher;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.easy.query.core.lambda.util.ExpressionUtil.*;
import static com.easy.query.core.lambda.util.SqlUtil.*;


public class GroupByVisitor extends BaseVisitor
{
    private final Map<String, GroupExtData> groupExtDataMap = new HashMap<>();
    private GroupExtData cur;

    public Map<String, GroupExtData> getGroupExtDataMap()
    {
        return groupExtDataMap;
    }

    public GroupByVisitor(List<ParameterExpression> parameters, DbType dbType)
    {
        super(parameters, dbType);
    }

    @Override
    public void visit(NewExpression newExpression)
    {
        BlockExpression classBody = newExpression.getClassBody();
        if (classBody == null) return;
        List<Expression> expressions = classBody.getExpressions();
        for (int i = 0; i < expressions.size(); i++)
        {
            visit(expressions.get(i));
            if (i < expressions.size() - 1)
            {
                data.append(",");
            }
        }
    }

    @Override
    public void visit(VariableExpression variable)
    {
        cur = new GroupExtData();
        visit(variable.getInit());
        groupExtDataMap.put(variable.getName(), cur);
        cur = null;
    }

    @Override
    public void visit(MethodCallExpression methodCall)
    {
        if (methodCall.getExpr().getKind() == Kind.Parameter)
        {
            ParameterExpression parameter = (ParameterExpression) methodCall.getExpr();
            if (parameters.contains(parameter) && !isVoid(methodCall.getMethod().getReturnType()))
            {
                int index = parameters.indexOf(parameter);
                String fn = fieldName(methodCall.getMethod());
                putField(index, fn);
                extracted(new SqlValue(SqlValue.Type.property, index, fn));
            }
            else
            {
                throw new IllegalExpressionException(methodCall);
            }
        }
        else if (SqlFunctions.class.isAssignableFrom(methodCall.getMethod().getDeclaringClass()))
        {
            visit(methodCall.getExpr());
            Method callMethod = methodCall.getMethod();
            SqlFunctions.Ext[] exts = callMethod.getAnnotationsByType(SqlFunctions.Ext.class);
            // 没有Ext注解的直接使用函数名
            if (exts.length == 0)
            {
                data.append(callMethod.getName()).append("(");
                if (cur != null)
                {
                    cur.exprData.append(callMethod.getName()).append("(");
                }
                List<Expression> args = methodCall.getArgs();
                for (int i = 0; i < args.size(); i++)
                {
                    Expression arg = args.get(i);
                    visit(arg);
                    if (i < args.size() - 1)
                    {
                        data.append(",");
                        if (cur != null)
                        {
                            cur.exprData.append(",");
                        }
                    }
                }
                data.append(")");
                if (cur != null) cur.exprData.append(")");
            }
            else
            {
                String function = getTargetSqlFuncExt(exts).function();
                if (methodCall.getArgs().isEmpty())
                {
                    data.append(function);
                    if (cur != null) cur.exprData.append(function);
                }
                else if (function.contains("{}"))
                {
                    String[] splitFunc = function.split("\\{}");
                    List<Expression> args = methodCall.getArgs();
                    for (int i = 0; i < splitFunc.length; i++)
                    {
                        data.append(splitFunc[i]);
                        if (cur != null) cur.exprData.append(splitFunc[i]);
                        // 可变参数情况
                        if (i == splitFunc.length - 2
                                && args.size() >= splitFunc.length)
                        {
                            while (i < args.size())
                            {
                                visit(args.get(i));
                                if (i < args.size() - 1)
                                {
                                    data.append(",");
                                    if (cur != null) cur.exprData.append(",");
                                }
                                i++;
                            }
                            data.append(splitFunc[splitFunc.length - 1]);
                            if (cur != null) cur.exprData.append(splitFunc[splitFunc.length - 1]);
                        }
                        // 正常情况
                        else if (i < args.size()) visit(args.get(i));
                    }
                }
                else if (function.contains("{") && function.contains("}"))
                {
                    List<Expression> args = methodCall.getArgs();
                    List<Parameter> methodParameters = Arrays.stream(methodCall.getMethod().getParameters()).collect(Collectors.toList());
                    ParamMatcher match = match(function);
                    List<String> functions = match.remainder;
                    List<String> params = match.bracesContent;
                    for (int i = 0; i < functions.size(); i++)
                    {
                        data.append(functions.get(i));
                        if (cur != null) cur.exprData.append(functions.get(i));
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
                                    if (index < args.size() - 1)
                                    {
                                        data.append(",");
                                        if (cur != null) cur.exprData.append(",");
                                    }
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
        }
        else if (SqlTypes.class.isAssignableFrom(methodCall.getMethod().getDeclaringClass()))
        {
            visit(methodCall.getExpr());
            Method callMethod = methodCall.getMethod();
            SqlTypes.Ext[] exts = callMethod.getAnnotationsByType(SqlTypes.Ext.class);

            if (exts.length == 0)
            {
                data.append(callMethod.getName());
                if (cur != null)
                {
                    cur.exprData.append(callMethod.getName()).append("(");
                }
                if (!methodCall.getArgs().isEmpty())
                {
                    data.append("(");
                    List<Expression> args = methodCall.getArgs();
                    for (int i = 0; i < args.size(); i++)
                    {
                        Expression arg = args.get(i);
                        visit(arg);
                        if (i < args.size() - 1)
                        {
                            data.append(",");
                            if (cur != null)
                            {
                                cur.exprData.append(",");
                            }
                        }
                    }
                    data.append(")");
                    if (cur != null)
                    {
                        cur.exprData.append(")");
                    }
                }
            }
            else
            {
                String function = getSqlTypeExtByMethod(exts).type();
                if (methodCall.getArgs().isEmpty())
                {
                    data.append(function);
                    if (cur != null) cur.exprData.append(function);
                }
                else if (function.contains("{}"))
                {
                    String[] splitFunc = function.split("\\{}");
                    List<Expression> args = methodCall.getArgs();
                    for (int i = 0; i < splitFunc.length; i++)
                    {
                        data.append(splitFunc[i]);
                        if (cur != null) cur.exprData.append(splitFunc[i]);
                        // 可变参数情况
                        if (i == splitFunc.length - 2
                                && args.size() >= splitFunc.length)
                        {
                            while (i < args.size())
                            {
                                visit(args.get(i));
                                if (i < args.size() - 1)
                                {
                                    data.append(",");
                                    if (cur != null) cur.exprData.append(",");
                                }
                                i++;
                            }
                            data.append(splitFunc[splitFunc.length - 1]);
                            if (cur != null) cur.exprData.append(splitFunc[splitFunc.length - 1]);
                        }
                        // 正常情况
                        else if (i < args.size()) visit(args.get(i));
                    }
                }
                else if (function.contains("{") && function.contains("}"))
                {
                    List<Expression> args = methodCall.getArgs();
                    List<Parameter> methodParameters = Arrays.stream(methodCall.getMethod().getParameters()).collect(Collectors.toList());
                    ParamMatcher match = match(function);
                    List<String> functions = match.remainder;
                    List<String> params = match.bracesContent;
                    for (int i = 0; i < functions.size(); i++)
                    {
                        data.append(functions.get(i));
                        if (cur != null) cur.exprData.append(functions.get(i));
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
                                    if (index < args.size() - 1)
                                    {
                                        data.append(",");
                                        if (cur != null) cur.exprData.append(",");
                                    }
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
            if (parameters.contains(parameter) && !isVoid(fieldSelect.getField().getType()))
            {
                int index = parameters.indexOf(parameter);
                String fn = fieldName(fieldSelect.getField());
                putField(index, fn);
                extracted(new SqlValue(SqlValue.Type.property, index, fn));
            }
            else
            {
                throw new IllegalExpressionException(fieldSelect);
            }
        }
        else
        {
            tryPutExprValue(fieldSelect);
        }
    }

    @Override
    public void visit(ConstantExpression constant)
    {
        putValue(constant.getValue());
        extracted(new SqlValue(constant.getValue()));
    }

    private void extracted(SqlValue sqlValue)
    {
        if (cur != null)
        {
            cur.exprData.append("{}");
            cur.sqlValues.add(sqlValue);
        }
        else
        {
            GroupExtData groupExtData = new GroupExtData();
            groupExtData.exprData.append("{}");
            groupExtData.sqlValues.add(sqlValue);
            groupExtDataMap.put("key", groupExtData);
        }
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        putValue(reference.getValue());
        extracted(new SqlValue(reference.getValue()));
    }
}
