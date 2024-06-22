package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.api.lambda.db.DbType;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.api.lambda.sqlext.SqlTypes;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.easy.query.core.lambda.util.ExpressionUtil.hasParameter;
import static com.easy.query.core.lambda.util.ExpressionUtil.isVoid;
import static com.easy.query.core.lambda.util.SqlUtil.fieldName;

public class GroupByReader extends BaseVisitor
{
    private final Map<String, GroupExtData> groupExtDataMap = new HashMap<>();
    private GroupExtData cur = new GroupExtData();

    public GroupByReader(List<ParameterExpression> parameters, DbType dbType)
    {
        super(parameters, dbType);
    }

    public Map<String, GroupExtData> getGroupExtDataMap()
    {
        return groupExtDataMap;
    }

    public GroupExtData getCur()
    {
        return cur;
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
                putSqlValue(new SqlValue(SqlValue.Type.property, index, fn));
            }
            else
            {
                throw new IllegalExpressionException(methodCall);
            }
        }
        else if (SqlFunctions.class.isAssignableFrom(methodCall.getMethod().getDeclaringClass()))
        {
            visit(methodCall.getExpr());
            matchSqlFunctions(methodCall, cur.exprData);
        }
        else if (SqlTypes.class.isAssignableFrom(methodCall.getMethod().getDeclaringClass()))
        {
            visit(methodCall.getExpr());
            matchSqlTypes(methodCall, cur.exprData);
        }
        else
        {
            //methodCallVisitor(methodCall);
            throw new RuntimeException(methodCall.toString());
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
                putSqlValue(new SqlValue(SqlValue.Type.property, index, fn));
            }
            else
            {
                throw new IllegalExpressionException(fieldSelect);
            }
        }
        else
        {
            if (hasParameter(fieldSelect) || isVoid(fieldSelect.getField().getType()))
            {
                throw new IllegalExpressionException(fieldSelect);
            }
            putSqlValue(new SqlValue(fieldSelect.getValue()));
        }
    }

    @Override
    public void visit(ConstantExpression constant)
    {
        putSqlValue(new SqlValue(constant.getValue()));
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        putSqlValue(new SqlValue(reference.getValue()));
    }

    private void putSqlValue(SqlValue sqlValue)
    {
        cur.exprData.append("{}");
        cur.sqlValues.add(sqlValue);
    }
}
