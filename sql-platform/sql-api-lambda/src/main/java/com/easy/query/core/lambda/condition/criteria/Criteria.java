package com.easy.query.core.lambda.condition.criteria;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.*;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import com.easy.query.core.lambda.visitor.GroupByVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.Kind;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

import java.util.List;

public abstract class Criteria
{
    protected void checkExprBody(LambdaExpression<?> lambdaExpression)
    {
        if (lambdaExpression.getBody().getKind() == Kind.Block)
        {
            throw new IllegalExpressionException(lambdaExpression);
        }
    }

    protected void analysis0(ClientQueryable<?> clientQueryable, QueryData queryData, String sql, List<SqlValue> sqlValues)
    {
        if (clientQueryable instanceof ClientQueryable10)
        {
            ClientQueryable10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> queryable = (ClientQueryable10<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4, w5, w6, w7, w8, w9) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                                case 5:
                                    s.expression(w5, sqlValue.value.toString());
                                    break;
                                case 6:
                                    s.expression(w6, sqlValue.value.toString());
                                    break;
                                case 7:
                                    s.expression(w7, sqlValue.value.toString());
                                    break;
                                case 8:
                                    s.expression(w8, sqlValue.value.toString());
                                    break;
                                case 9:
                                    s.expression(w9, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable9)
        {
            ClientQueryable9<?, ?, ?, ?, ?, ?, ?, ?, ?> queryable = (ClientQueryable9<?, ?, ?, ?, ?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4, w5, w6, w7, w8) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                                case 5:
                                    s.expression(w5, sqlValue.value.toString());
                                    break;
                                case 6:
                                    s.expression(w6, sqlValue.value.toString());
                                    break;
                                case 7:
                                    s.expression(w7, sqlValue.value.toString());
                                    break;
                                case 8:
                                    s.expression(w8, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable8)
        {
            ClientQueryable8<?, ?, ?, ?, ?, ?, ?, ?> queryable = (ClientQueryable8<?, ?, ?, ?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4, w5, w6, w7) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                                case 5:
                                    s.expression(w5, sqlValue.value.toString());
                                    break;
                                case 6:
                                    s.expression(w6, sqlValue.value.toString());
                                    break;
                                case 7:
                                    s.expression(w7, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable7)
        {
            ClientQueryable7<?, ?, ?, ?, ?, ?, ?> queryable = (ClientQueryable7<?, ?, ?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4, w5, w6) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                                case 5:
                                    s.expression(w5, sqlValue.value.toString());
                                    break;
                                case 6:
                                    s.expression(w6, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable6)
        {
            ClientQueryable6<?, ?, ?, ?, ?, ?> queryable = (ClientQueryable6<?, ?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4, w5) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                                case 5:
                                    s.expression(w5, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable5)
        {
            ClientQueryable5<?, ?, ?, ?, ?> queryable = (ClientQueryable5<?, ?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3, w4) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                                case 4:
                                    s.expression(w4, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable4)
        {
            ClientQueryable4<?, ?, ?, ?> queryable = (ClientQueryable4<?, ?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2, w3) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                                case 3:
                                    s.expression(w3, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable3)
        {
            ClientQueryable3<?, ?, ?> queryable = (ClientQueryable3<?, ?, ?>) clientQueryable;
            queryable.groupBy((w0, w1, w2) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                                case 2:
                                    s.expression(w2, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else if (clientQueryable instanceof ClientQueryable2)
        {
            ClientQueryable2<?, ?> queryable = (ClientQueryable2<?, ?>) clientQueryable;
            queryable.groupBy((w0, w1) -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
                {
                    switch (sqlValue.type)
                    {
                        case value:
                            s.value(sqlValue.value);
                            break;
                        case property:
                            switch (sqlValue.index)
                            {
                                case 0:
                                    s.expression(w0, sqlValue.value.toString());
                                    break;
                                case 1:
                                    s.expression(w1, sqlValue.value.toString());
                                    break;
                            }
                            break;
                    }
                }
            }));
        }
        else
        {
            clientQueryable.groupBy(w0 -> w0.sqlNativeSegment(sql, s ->
            {
                for (SqlValue sqlValue : sqlValues)
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
                            break;
                    }
                }
            }));
        }
    }
}
