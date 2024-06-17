package com.easy.query.core.lambda.condition.groupBy;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.GroupByReader;
import com.easy.query.core.lambda.visitor.GroupByVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

import java.util.Map;


public class GroupBy extends Criteria
{
    private final LambdaExpression<?> expression;

    public GroupBy(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    private void readGroup(QueryData queryData)
    {
        GroupByReader groupByReader = new GroupByReader(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupByReader);
        Map<String, GroupExtData> groupExtDataMap = groupByReader.getGroupExtDataMap();
        // 空的GroupExtData说明碰到了非new class的情况，手动包装一下
        if (groupExtDataMap.isEmpty())
        {
            groupExtDataMap.put("key", groupByReader.getCur());
        }
        queryData.setGroupExtDataMap(groupExtDataMap);
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        readGroup(queryData);
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);
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
        readGroup(queryData);
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);
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
        readGroup(queryData);
        GroupByVisitor groupBy = new GroupByVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(groupBy);
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
