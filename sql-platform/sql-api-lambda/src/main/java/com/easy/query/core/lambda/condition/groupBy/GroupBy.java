package com.easy.query.core.lambda.condition.groupBy;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.exception.IllegalExpressionException;
import com.easy.query.core.lambda.visitor.GroupByVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.easy.query.core.lambda.util.ExpressionUtil.*;
import static com.easy.query.core.lambda.util.SqlUtil.*;


public class GroupBy extends Criteria
{
    private final LambdaExpression<?> expression;

    public GroupBy(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    private void readGroup()
    {

    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);

        queryData.setGroupExtDataMap(groupBy.getGroupExtDataMap());

        queryable.groupBy(w -> w.sqlNativeSegment(groupBy.getData(), s ->
        {
            for (SqlValue sqlValue : groupBy.getSqlValue())
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

    public void analysis(ClientQueryable2<?, ?> queryable, QueryData queryData)
    {
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);
        queryData.setGroupExtDataMap(groupBy.getGroupExtDataMap());

        queryable.groupBy((w0, w1) -> w0.sqlNativeSegment(groupBy.getData(), s ->
        {
            for (SqlValue sqlValue : groupBy.getSqlValue())
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
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);
        queryData.setGroupExtDataMap(groupBy.getGroupExtDataMap());

        queryable.groupBy((w0, w1, w2) -> w0.sqlNativeSegment(groupBy.getData(), s ->
        {
            for (SqlValue sqlValue : groupBy.getSqlValue())
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
