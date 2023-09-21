package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.AnonymousEntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;

import java.util.Collection;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousEntityQueryExpressionBuilder {
    private final String sql;
    private final Collection<Object> sqlParams;

    public AnonymousQueryExpressionBuilder(String sql, Collection<Object> sqlParams, ExpressionContext queryExpressionContext, Class<?> queryClass) {
        super(queryExpressionContext, queryClass);
        this.sql = sql;
        this.sqlParams = sqlParams;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public EntityQuerySQLExpression toExpression() {
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        AnonymousEntityQuerySQLExpression easyAnonymousQuerySQLExpression = runtimeContext.getExpressionFactory().createEasyAnonymousQuerySQLExpression(entitySQLExpressionMetadata, sql, sqlParams);
        easyAnonymousQuerySQLExpression.setProjects(this.projects);
        return easyAnonymousQuerySQLExpression;
    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {

        EntityQueryExpressionBuilder anonymousQueryExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createAnonymousQueryExpressionBuilder(sql, sqlParams, expressionContext.cloneExpressionContext(), queryClass);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}
