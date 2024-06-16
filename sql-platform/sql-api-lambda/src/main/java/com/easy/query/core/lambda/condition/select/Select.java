package com.easy.query.core.lambda.condition.select;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.SelectVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

public class Select extends Criteria
{
    private final LambdaExpression<?> expression;

    public Select(LambdaExpression<?> lambdaExpression)
    {
        this.expression = lambdaExpression;
    }

    public <T, R> ClientQueryable<R> analysis(ClientQueryable<T> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), w -> w.sqlNativeSegment(select.getData(), s ->
        {
            for (SqlValue sqlValue : select.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            s.expression(sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));

    }

    public <T1, T2, R> ClientQueryable<R> analysis(ClientQueryable2<T1, T2> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w1, w2) -> w1.sqlNativeSegment(select.getData(), s ->
        {
            for (SqlValue sqlValue : select.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        if (sqlValue.index == 0)
                        {
                            s.expression(w1, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 1)
                        {
                            s.expression(w2, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, R> ClientQueryable<R> analysis(ClientQueryable3<T1, T2, T3> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2) -> w0.sqlNativeSegment(select.getData(), s ->
        {
            for (SqlValue sqlValue : select.getSqlValue())
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
