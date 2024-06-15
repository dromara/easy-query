package com.easy.query.core.lambda.condition.having;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.HavingVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;


public class Having extends Criteria
{
    private final LambdaExpression<?> expression;

    public Having(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        HavingVisitor having = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(having);
        queryable.having(w -> w.sqlNativeSegment(having.getData(), s ->
        {
            for (SqlValue sqlValue : having.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            s.expression(w, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public void analysis(ClientQueryable2<?, ?> queryable, QueryData queryData)
    {
        HavingVisitor having = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(having);
        queryable.having((w0, w1) -> w0.sqlNativeSegment(having.getData(), s ->
        {
            for (SqlValue sqlValue : having.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            s.expression(w0, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 1)
                        {
                            s.expression(w1, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public void analysis(ClientQueryable3<?, ?, ?> queryable, QueryData queryData)
    {
        HavingVisitor having = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(having);
        queryable.having((w0, w1, w2) -> w0.sqlNativeSegment(having.getData(), s ->
        {
            for (SqlValue sqlValue : having.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            s.expression(w0, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 1)
                        {
                            s.expression(w1, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 2)
                        {
                            s.expression(w2, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }
}
