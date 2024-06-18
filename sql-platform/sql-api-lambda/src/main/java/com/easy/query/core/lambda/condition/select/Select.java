package com.easy.query.core.lambda.condition.select;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.*;
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

    // region [AutoInclude]

    public <T, R> Query<R> analysisAutoInclude(ClientQueryable<T> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), w -> w.sqlNativeSegment(select.getData(), s ->
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
        }), false);
    }

    public <T1, T2, R> Query<R> analysisAutoInclude(ClientQueryable2<T1, T2> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, R> Query<R> analysisAutoInclude(ClientQueryable3<T1, T2, T3> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2) -> w0.sqlNativeSegment(select.getData(), s ->
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
        }), false);
    }

    public <T1, T2, T3, T4, R> Query<R> analysisAutoInclude(ClientQueryable4<T1, T2, T3, T4> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, R> Query<R> analysisAutoInclude(ClientQueryable5<T1, T2, T3, T4, T5> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, T6, R> Query<R> analysisAutoInclude(ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, T6, T7, R> Query<R> analysisAutoInclude(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, R> Query<R> analysisAutoInclude(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Query<R> analysisAutoInclude(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7, w8) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 8)
                        {
                            s.expression(w8, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> Query<R> analysisAutoInclude(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.selectAutoInclude((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 8)
                        {
                            s.expression(w8, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 9)
                        {
                            s.expression(w9, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }), false);
    }

    // endregion

    // region [normal]

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
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1) -> w0.sqlNativeSegment(select.getData(), s ->
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

    public <T1, T2, T3, T4, R> ClientQueryable<R> analysis(ClientQueryable4<T1, T2, T3, T4> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, R> ClientQueryable<R> analysis(ClientQueryable5<T1, T2, T3, T4, T5> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, T6, R> ClientQueryable<R> analysis(ClientQueryable6<T1, T2, T3, T4, T5, T6> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, R> ClientQueryable<R> analysis(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, R> ClientQueryable<R> analysis(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> ClientQueryable<R> analysis(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7, w8) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 8)
                        {
                            s.expression(w8, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> ClientQueryable<R> analysis(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable, QueryData queryData)
    {
        SelectVisitor select = new SelectVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(select);
        return queryable.select((Class<R>) expression.getReturnType(), (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) -> w0.sqlNativeSegment(select.getData(), s ->
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
                        else if (sqlValue.index == 3)
                        {
                            s.expression(w3, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 4)
                        {
                            s.expression(w4, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 5)
                        {
                            s.expression(w5, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 6)
                        {
                            s.expression(w6, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 7)
                        {
                            s.expression(w7, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 8)
                        {
                            s.expression(w8, sqlValue.value.toString());
                        }
                        else if (sqlValue.index == 9)
                        {
                            s.expression(w9, sqlValue.value.toString());
                        }
                        break;
                }
            }
        }));
    }

    // endregion
}
