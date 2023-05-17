package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.enums.SQLUnionEnum;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousUnionEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousUnionQuerySQLExpressionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousUnionQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousUnionEntityQueryExpressionBuilder {

    private final List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders;
    private final SQLUnionEnum sqlUnion;

    public AnonymousUnionQueryExpressionBuilder(List<EntityQueryExpressionBuilder> entityQueryExpressionBuilders, ExpressionContext queryExpressionContext, SQLUnionEnum sqlUnion) {
        super(queryExpressionContext);
        this.entityQueryExpressionBuilders = entityQueryExpressionBuilders;
        this.sqlUnion = sqlUnion;
    }

    @Override
    public List<EntityQueryExpressionBuilder> getEntityQueryExpressionBuilders() {
        return entityQueryExpressionBuilders;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public QuerySQLExpression toExpression() {
       List<QuerySQLExpression> querySQLExpressions = new ArrayList<>(entityQueryExpressionBuilders.size());
        for (EntityQueryExpressionBuilder entityQueryExpressionBuilder : entityQueryExpressionBuilders) {

            QuerySQLExpression expression = entityQueryExpressionBuilder.toExpression();
            querySQLExpressions.add(expression);
        }
        return new AnonymousUnionQuerySQLExpressionImpl(getRuntimeContext(), querySQLExpressions,sqlUnion);
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        AnonymousUnionQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousUnionQueryExpressionBuilder(entityQueryExpressionBuilders, sqlExpressionContext,sqlUnion);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}
