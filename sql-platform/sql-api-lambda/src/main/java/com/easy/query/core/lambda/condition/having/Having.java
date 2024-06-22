package com.easy.query.core.lambda.condition.having;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.HavingVisitor;
import com.easy.query.core.lambda.visitor.SqlValue;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;


public class Having extends Criteria
{
    private final LambdaExpression<?> expression;

    public Having(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        HavingVisitor having = new HavingVisitor(expression.getParameters(), queryData);
        expression.getBody().accept(having);
        analysisHaving(queryable,queryData,having.getData(),having.getSqlValue());
    }
}
