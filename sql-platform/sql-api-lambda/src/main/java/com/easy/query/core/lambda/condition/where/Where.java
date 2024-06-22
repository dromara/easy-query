package com.easy.query.core.lambda.condition.where;

import com.easy.query.api.lambda.crud.read.QueryData;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.lambda.condition.criteria.Criteria;
import com.easy.query.core.lambda.visitor.SqlValue;
import com.easy.query.core.lambda.visitor.WhereVisitor;
import io.github.kiryu1223.expressionTree.expressions.LambdaExpression;

public class Where extends Criteria
{
    private final LambdaExpression<?> expression;

    public Where(LambdaExpression<?> expression)
    {
        checkExprBody(expression);
        this.expression = expression;
    }

    public void analysis(ClientExpressionDeletable<?> deletable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(where);
        deletable.where(w -> w.sqlNativeSegment(where.getData(), s ->
        {
            for (SqlValue sqlValue : where.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        s.expression(sqlValue.value.toString());
                        break;
                }
            }
        }));
    }

    public void analysis(ClientExpressionUpdatable<?> updatable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(where);
        updatable.where(w -> w.sqlNativeSegment(where.getData(), s ->
        {
            for (SqlValue sqlValue : where.getSqlValue())
            {
                switch (sqlValue.type)
                {
                    case value:
                        s.value(sqlValue.value);
                        break;
                    case property:
                        s.expression(sqlValue.value.toString());
                        break;
                }
            }
        }));
    }

    public void analysis(ClientQueryable<?> queryable, QueryData queryData)
    {
        WhereVisitor where = new WhereVisitor(expression.getParameters(), queryData.getDbType());
        expression.getBody().accept(where);
        analysis0(queryable,queryData,where.getData(),where.getSqlValue());
    }
}
