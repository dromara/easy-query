package com.easy.query.core.lambda.condition.groupBy;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.api.lambda.crud.read.group.GroupExtData;
import com.easy.query.core.basic.api.select.*;
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
        analysis0(queryable,queryData,groupBy.getData(),groupBy.getSqlValue());
    }
}
