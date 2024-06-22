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
        analysisOrderBy(queryable, queryData, orderBy.getData() + (asc ? " ASC" : " DESC"), orderBy.getSqlValue());
    }
}
