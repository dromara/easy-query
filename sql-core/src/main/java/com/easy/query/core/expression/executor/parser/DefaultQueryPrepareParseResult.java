package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

import java.util.Set;

/**
 * create time 2023/4/24 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultQueryPrepareParseResult implements PrepareParseResult, QueryPrepareParseResult {
    private final Set<Class<?>> shardingEntities;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final EasyQuerySqlExpression easyQuerySqlExpression;

    public DefaultQueryPrepareParseResult(Set<Class<?>> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        this.shardingEntities = shardingEntities;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.easyQuerySqlExpression = entityQueryExpressionBuilder.toExpression();
    }

    @Override
    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    @Override
    public EntityQueryExpressionBuilder getEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public EasyQuerySqlExpression getEasyQuerySqlExpression() {
        return easyQuerySqlExpression;
    }
}
