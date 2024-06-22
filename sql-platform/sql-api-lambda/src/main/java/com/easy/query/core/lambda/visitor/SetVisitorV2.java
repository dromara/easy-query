package com.easy.query.core.lambda.visitor;

import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.lambda.visitor.context.SqlSetPair;
import io.github.kiryu1223.expressionTree.expressions.*;

import static com.easy.query.core.lambda.util.SqlUtil.fieldName;

public class SetVisitorV2 extends DeepFindVisitor
{
    private final ClientExpressionUpdatable<?> clientExpressionUpdatable;
    private SqlSetPair sqlSetPairContext;


    public SetVisitorV2(ClientExpressionUpdatable<?> clientExpressionUpdatable)
    {
        this.clientExpressionUpdatable = clientExpressionUpdatable;
    }

    @Override
    public void visit(MethodCallExpression methodCallExpression)
    {
        if (methodCallExpression.getExpr().getKind() == Kind.Parameter)
        {
            ParameterExpression parameter = (ParameterExpression) methodCallExpression.getExpr();
            sqlSetPairContext = new SqlSetPair();
            sqlSetPairContext.setProperty(fieldName(methodCallExpression.getMethod()));
            for (Expression arg : methodCallExpression.getArgs())
            {
                visit(arg);
            }
            sqlSetPairContext.updatable(clientExpressionUpdatable);
            sqlSetPairContext = null;
        }
    }
}
