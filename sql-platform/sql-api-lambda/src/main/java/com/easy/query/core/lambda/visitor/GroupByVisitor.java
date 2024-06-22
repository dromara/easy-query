package com.easy.query.core.lambda.visitor;

import com.easy.query.api.lambda.db.DbType;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.util.List;

import static com.easy.query.core.lambda.util.ExpressionUtil.isVoid;
import static com.easy.query.core.lambda.util.SqlUtil.fieldName;


public class GroupByVisitor extends BaseVisitor
{
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
            if (parameters.contains(parameter) && !isVoid(fieldSelect.getField().getType()))
            {
                int index = parameters.indexOf(parameter);
                String fn = fieldName(fieldSelect.getField());
                putField(index, fn);
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
    }

    @Override
    public void visit(ReferenceExpression reference)
    {
        putValue(reference.getValue());
    }
}
