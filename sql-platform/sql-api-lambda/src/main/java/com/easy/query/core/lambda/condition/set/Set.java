package com.easy.query.core.lambda.condition.set;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.Pair;
import com.easy.query.core.lambda.visitor.SetVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

import java.util.List;

public class Set extends Criteria
{
    private final LambdaExpression<?> expression;

    public Set(LambdaExpression<?> expression)
    {
        this.expression = expression;
    }

    public void analysis(ClientExpressionUpdatable<?> updatable, QueryData queryData)
    {
        SetVisitor set = new SetVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(set);
        List<Pair> pairs = set.getPairs();
        for (Pair pair : pairs)
        {
            updatable.setSQLSegment(pair.property, pair.sqlSegment.toString(), u0 ->
            {
                for (SqlValue sqlValue : pair.sqlValue)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            u0.value(sqlValue.value);
                            break;
                        case property:
                            u0.expression(sqlValue.value.toString());
                            break;
                    }
                }
            });
        }
    }
}
