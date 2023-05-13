package com.easy.query.core.expression.executor.parser;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.metadata.ShardingInitConfig;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.metadata.ShardingSequenceConfig;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.ShardingUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.Objects;
import java.util.Set;

/**
 * create time 2023/4/24 22:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryPrepareParseResult implements QueryPrepareParseResult {
    private final ExecutorContext executorContext;
    private final Set<TableAvailable> shardingTables;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final EasyQuerySqlExpression easyQuerySqlExpression;
    private final boolean sharding;
    private boolean startsWithGroupByInOrderBy;
    private int maxShardingQueryLimit;
    private ConnectionModeEnum connectionMode;
    private final long offset;
    private final long rows;
    private final SequenceParseResult sequenceParseResult;

    public EasyQueryPrepareParseResult(ExecutorContext executorContext, Set<TableAvailable> shardingEntities, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.executorContext = executorContext;

        this.shardingTables = shardingEntities;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.easyQuerySqlExpression = entityQueryExpressionBuilder.toExpression();
        this.sharding = EasyCollectionUtil.isNotEmpty(shardingEntities);
        this.offset = easyQuerySqlExpression.getOffset();
        this.rows = easyQuerySqlExpression.getRows();
        this.sequenceParseResult = initSequenceOrderPrepareParseResult(executorContext);
        this.maxShardingQueryLimit= ShardingUtil.getMaxShardingQueryLimit(entityQueryExpressionBuilder,sequenceParseResult);
        this.connectionMode=ShardingUtil.getConnectionMode(entityQueryExpressionBuilder,sequenceParseResult);
    }

    private SequenceParseResult initSequenceOrderPrepareParseResult(ExecutorContext executorContext) {
        EasyQueryOption easyQueryOption = executorContext.getRuntimeContext().getEasyQueryConfiguration().getEasyQueryOption();
        //存在分片对象的情况下
        if (EasyCollectionUtil.isNotEmpty(shardingTables)) {
            SqlBuilderSegment order = easyQuerySqlExpression.getOrder();
            if (SqlSegmentUtil.isNotEmpty(order)) {
                SqlSegment firstOrder = EasyCollectionUtil.first(order.getSqlSegments());
                OrderByColumnSegment firstOrderColumn = (OrderByColumnSegment) firstOrder;
                TableAvailable table = firstOrderColumn.getTable();
                ShardingInitConfig shardingInitConfig = table.getEntityMetadata().getShardingInitConfig();
                ShardingSequenceConfig shardingSequenceConfig = shardingInitConfig.getShardingSequenceConfig();
                if (shardingSequenceConfig != null) {
                    //存在配置
                    Boolean asc = shardingSequenceConfig.getSequenceProperty(firstOrderColumn.getPropertyName());
                    if (asc != null) {
                        boolean reverse = !firstOrderColumn.isAsc();
                        return new SequenceParseResult(table, shardingSequenceConfig.getTableComparator(),reverse ,shardingSequenceConfig.getConnectionModeOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getConnectionMode()), shardingSequenceConfig.getMaxShardingQueryLimitOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getMaxShardingQueryLimit()));
                    }
                }
            } else {

                if (Objects.equals(ExecuteMethodEnum.MAX, executorContext.getExecuteMethod())) {
                    SqlSegment firstMax = EasyCollectionUtil.first(easyQuerySqlExpression.getProjects().getSqlSegments());
                    if (firstMax instanceof AggregationColumnSegment) {
                        AggregationColumnSegment firstMaxColumn = (AggregationColumnSegment) firstMax;

                        TableAvailable table = firstMaxColumn.getTable();
                        ShardingInitConfig shardingInitConfig = table.getEntityMetadata().getShardingInitConfig();
                        ShardingSequenceConfig shardingSequenceConfig = shardingInitConfig.getShardingSequenceConfig();
                        if (shardingSequenceConfig != null) {
                            //存在配置
                            Boolean asc = shardingSequenceConfig.getSequenceProperty(firstMaxColumn.getPropertyName());
                            if (asc != null) {
                                return new SequenceParseResult(table, shardingSequenceConfig.getTableComparator(), true,shardingSequenceConfig.getConnectionModeOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getConnectionMode()), shardingSequenceConfig.getMaxShardingQueryLimitOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getMaxShardingQueryLimit()));
                            }
                        }
                    }

                } else if (Objects.equals(ExecuteMethodEnum.MIN, executorContext.getExecuteMethod())) {
                    SqlSegment firstMin = EasyCollectionUtil.first(easyQuerySqlExpression.getProjects().getSqlSegments());
                    if (firstMin instanceof AggregationColumnSegment) {
                        AggregationColumnSegment firstMinColumn = (AggregationColumnSegment) firstMin;

                        TableAvailable table = firstMinColumn.getTable();
                        ShardingInitConfig shardingInitConfig = table.getEntityMetadata().getShardingInitConfig();
                        ShardingSequenceConfig shardingSequenceConfig = shardingInitConfig.getShardingSequenceConfig();
                        if (shardingSequenceConfig != null) {
                            //存在配置
                            Boolean asc = shardingSequenceConfig.getSequenceProperty(firstMinColumn.getPropertyName());
                            if (asc != null) {
                                return new SequenceParseResult(table, shardingSequenceConfig.getTableComparator(), false,shardingSequenceConfig.getConnectionModeOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getConnectionMode()), shardingSequenceConfig.getMaxShardingQueryLimitOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getMaxShardingQueryLimit()));
                            }
                        }
                    }
                } else {

                    //默认匹配顺序
                    TableAvailable table = EasyCollectionUtil.first(shardingTables);
                    ShardingInitConfig shardingInitConfig = table.getEntityMetadata().getShardingInitConfig();
                    ShardingSequenceConfig shardingSequenceConfig = shardingInitConfig.getShardingSequenceConfig();
                    if (shardingSequenceConfig != null && shardingSequenceConfig.hasCompareMethods(executorContext.getExecuteMethod())) {
                        boolean asc = shardingSequenceConfig.hasCompareAscMethods(executorContext.getExecuteMethod());
                        boolean reverse = !asc;
                        return new SequenceParseResult(table, shardingSequenceConfig.getTableComparator(), reverse,shardingSequenceConfig.getConnectionModeOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getConnectionMode()), shardingSequenceConfig.getMaxShardingQueryLimitOrDefault(executorContext.getExecuteMethod(),easyQueryOption.getMaxShardingQueryLimit()));
                    }
                }
            }
        }
        return null;
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
    public EntityQueryExpressionBuilder getEntityExpressionBuilder() {
        return entityQueryExpressionBuilder;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public long getRows() {
        return rows;
    }

    @Override
    public boolean isSharding() {
        return sharding;
    }

    @Override
    public boolean isStartsWithGroupByInOrderBy() {
        return startsWithGroupByInOrderBy;
    }

    @Override
    public void setStartsWithGroupByInOrderBy(boolean startsWithGroupByInOrderBy) {
        this.startsWithGroupByInOrderBy = startsWithGroupByInOrderBy;
    }


    @Override
    public EasyQuerySqlExpression getEasyEntityPredicateSqlExpression() {
        return easyQuerySqlExpression;
    }

    @Override
    public SequenceParseResult getSequenceParseResult() {
        return sequenceParseResult;
    }

    @Override
    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    @Override
    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

}
