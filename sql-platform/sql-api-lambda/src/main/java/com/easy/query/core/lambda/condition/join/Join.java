package com.easy.query.core.lambda.condition.join;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.*;
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

    // region [4]
    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> innerJoin(Class<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> leftJoin(Class<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> rightJoin(Class<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, Tn> ClientQueryable5<T1, T2, T3, T4, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable4<T1, T2, T3, T4> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }


    // endregion

    // region [5]
    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> innerJoin(Class<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> leftJoin(Class<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> rightJoin(Class<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, Tn> ClientQueryable6<T1, T2, T3, T4, T5, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable5<T1, T2, T3, T4, T5> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion

    // region [6]
    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> innerJoin(Class<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> leftJoin(Class<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> rightJoin(Class<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, Tn> ClientQueryable7<T1, T2, T3, T4, T5, T6, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable6<T1, T2, T3, T4, T5, T6> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion

    // region [7]
    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> innerJoin(Class<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> leftJoin(Class<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> rightJoin(Class<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, Tn> ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    // endregion

    // region [8]
    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> innerJoin(Class<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> leftJoin(Class<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> rightJoin(Class<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, Tn> ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion

    // region [9]
    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> innerJoin(Class<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> innerJoin(ClientQueryable<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.innerJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> leftJoin(Class<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> leftJoin(ClientQueryable<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.leftJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> rightJoin(Class<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, Tn> rightJoin(ClientQueryable<Tn> target, ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> clientQueryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.accept(where);
        return clientQueryable.rightJoin(target, (w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) ->
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
                                else if (sqlValue.index == 4)
                                {
                                    g.expression(w4, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 5)
                                {
                                    g.expression(w5, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 6)
                                {
                                    g.expression(w6, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 7)
                                {
                                    g.expression(w7, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 8)
                                {
                                    g.expression(w8, sqlValue.value.toString());
                                }
                                else if (sqlValue.index == 9)
                                {
                                    g.expression(w9, sqlValue.value.toString());
                                }
                                break;
                        }
                    }
                }));
    }
    // endregion
}
