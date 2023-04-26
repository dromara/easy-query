package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;

import java.util.Set;

/**
 * create time 2023/4/26 11:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyPredicatePrepareParseResult implements PredicatePrepareParseResult{
    private final Set<Class<?>> shardingEntities;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final EasyEntityPredicateSqlExpression easyEntitySqlExpression;

    public EasyPredicatePrepareParseResult(Set<Class<?>> shardingEntities, EntityExpressionBuilder entityExpressionBuilder){

        this.shardingEntities = shardingEntities;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.easyEntitySqlExpression = (EasyEntityPredicateSqlExpression)entityExpressionBuilder.toExpression();
    }
    @Override
    public EasyEntityPredicateSqlExpression getEasyEntityPredicateSqlExpression() {
        return easyEntitySqlExpression;
    }

    @Override
    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }
}
