package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Set;

/**
 * create time 2023/4/26 11:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyPredicatePrepareParseResult implements PredicatePrepareParseResult{
    private final ExecutorContext executorContext;
    private final Set<TableAvailable> shardingTables;
    private final EntityPredicateExpressionBuilder entityPredicateExpressionBuilder;
    private final EasyEntityPredicateSqlExpression easyEntitySqlExpression;
    private final boolean sharding;

    public EasyPredicatePrepareParseResult(ExecutorContext executorContext,Set<TableAvailable> shardingTables, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder){
        this.executorContext = executorContext;

        this.shardingTables = shardingTables;
        this.entityPredicateExpressionBuilder = entityPredicateExpressionBuilder;
        this.easyEntitySqlExpression = entityPredicateExpressionBuilder.toExpression();
        this.sharding = EasyCollectionUtil.isNotEmpty(shardingTables);
    }
    @Override
    public EasyEntityPredicateSqlExpression getEasyEntityPredicateSqlExpression() {
        return easyEntitySqlExpression;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public ExecutorContext getExecutorContext() {
        return executorContext;
    }

    @Override
    public Set<TableAvailable> getShardingTables() {
        return shardingTables;
    }

    @Override
    public EntityPredicateExpressionBuilder getEntityExpressionBuilder() {
        return entityPredicateExpressionBuilder;
    }
}
