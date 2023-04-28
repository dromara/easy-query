package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
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
    private final EntityPredicateExpressionBuilder entityPredicateExpressionBuilder;
    private final EasyEntityPredicateSqlExpression easyEntitySqlExpression;

    public EasyPredicatePrepareParseResult(Set<Class<?>> shardingEntities, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder){

        this.shardingEntities = shardingEntities;
        this.entityPredicateExpressionBuilder = entityPredicateExpressionBuilder;
        this.easyEntitySqlExpression = entityPredicateExpressionBuilder.toExpression();
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
    public EntityPredicateExpressionBuilder getEntityExpressionBuilder() {
        return entityPredicateExpressionBuilder;
    }
}
