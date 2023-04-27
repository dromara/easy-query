package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;

import java.util.Set;

/**
 * create time 2023/4/24 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryPrepareParseResult implements QueryPrepareParseResult {
    private final Set<Class<?>> shardingEntities;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final EasyQuerySqlExpression easyQuerySqlExpression;

    public EasyQueryPrepareParseResult(Set<Class<?>> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        this.shardingEntities = shardingEntities;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.easyQuerySqlExpression = entityQueryExpressionBuilder.toExpression();
    }

    @Override
    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }


    @Override
    public EasyQuerySqlExpression getEasyEntityPredicateSqlExpression() {
        return easyQuerySqlExpression;
    }
}
