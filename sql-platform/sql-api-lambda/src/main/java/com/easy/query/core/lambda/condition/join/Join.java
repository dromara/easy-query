package com.easy.query.core.lambda.condition.join;

import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.SqlValue;
import com.easy.query.core.lambda.visitor.WhereVisitor;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

public class Join extends Criteria
{
    private final LambdaExpression<?> expression;

    public Join(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    // region [1]
    public <T, Tn> ClientQueryable2<T, Tn> innerJoin(Class<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T, Tn> ClientQueryable2<T, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T, Tn> ClientQueryable2<T, Tn> leftJoin(Class<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T, Tn> ClientQueryable2<T, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T, Tn> ClientQueryable2<T, Tn> rightJoin(Class<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T, Tn> ClientQueryable2<T, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable<T> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion

    // region [2]
    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> innerJoin(Class<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> leftJoin(Class<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> rightJoin(Class<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, Tn> ClientQueryable3<T1, T2, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable2<T1, T2> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion

    // region [3]
    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> innerJoin(Class<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> leftJoin(Class<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> rightJoin(Class<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, Tn> ClientQueryable4<T1, T2, T3, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable3<T1, T2, T3> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3) ->
                w1.sqlNativeSegment(where.getData(), g ->
                {
                    for (SqlValue sqlValue : where.getSqlValue())
                    {
                        switch (sqlValue.type)
                        {
                            case value:
                                g.value(sqlValue.value);
                                break;
                            case property:
                                if (sqlValue.index == 0)
                                {
                                    g.expression(w0, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 1)
                                {
                                    g.expression(w1, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 2)
                                {
                                    g.expression(w2, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 3)
                                {
                                    g.expression(w3, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion
}
