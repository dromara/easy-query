package com.easy.query.core.lambda.condition.orderby;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.HavingVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

public class OrderBy extends Criteria
{
    private final LambdaExpression<?> expression;
    private final boolean asc;

    public OrderBy(LambdaExpression<?> expression, boolean asc)
    {
        checkExprBody(expression);
        this.expression = expression;
        this.asc = asc;
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        HavingVisitor orderBy = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(orderBy);
        queryable.orderBy(s -> s.sqlNativeSegment(orderBy.getData() + (asc ? "" : "DESC"), g ->
        {
            for (SqlValue sqlValue : orderBy.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        g.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            g.expression(s, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), asc);
    }

    public void analysis(ClientQueryable2<?, ?> queryable, QueryData queryData)
    {
        HavingVisitor orderBy = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(orderBy);
        queryable.orderByAsc((w0, w1) -> w1.sqlNativeSegment(orderBy.getData() + (asc ? "" : " DESC"), s ->
        {
            for (SqlValue sqlValue : orderBy.getSqlValue())
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
        HavingVisitor orderBy = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(orderBy);
        queryable.orderByAsc((w0, w1, w2) -> w1.sqlNativeSegment(orderBy.getData() + (asc ? "" : " DESC"), s ->
        {
            for (SqlValue sqlValue : orderBy.getSqlValue())
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
