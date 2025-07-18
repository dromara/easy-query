package com.easy.query.core.util;

import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.EntityPropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.EntityPropertyOrder;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.enums.ShardingQueryInTransactionEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegment;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.metadata.ShardingInitConfig;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.rewrite.DefaultRewriteRouteUnit;
import com.easy.query.core.sharding.rewrite.RewriteRouteUnit;
import com.easy.query.core.sharding.rewrite.SequencePaginationRewriteRouteUnit;
import com.easy.query.core.sharding.route.RouteFilter;
import com.easy.query.core.sharding.router.RouteContext;
import com.easy.query.core.sharding.router.RoutePredicateDiscover;
import com.easy.query.core.sharding.router.RoutePredicateExpression;
import com.easy.query.core.sharding.router.RouteUnit;
import com.easy.query.core.sharding.router.descriptor.RouteDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/19 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingUtil {

    public static <T> RoutePredicateExpression<T> getRoutePredicateExpression(RouteDescriptor routeDescriptor,
                                                                              RouteFilter<T> routeFilter, boolean shardingTableRoute) {

        RoutePredicateDiscover<T> routePredicateDiscover = new RoutePredicateDiscover<T>(routeDescriptor, routeFilter, shardingTableRoute);
        return routePredicateDiscover.getRouteParseExpression();
    }

    public static PropertyOrder findFirstPropertyOrderNotNull(List<SQLSegment> selectColumns, OrderBySegment orderColumnSegment, EntityQuerySQLExpression easyQuerySQLExpression) {
        TableAvailable columnTable = orderColumnSegment.getTable();
        String propertyName = orderColumnSegment.getPropertyName();
        boolean asc = orderColumnSegment.isAsc();
        int selectIndex = -1;
        for (SQLSegment selectColumn : selectColumns) {
            selectIndex++;
            if (selectColumn instanceof ColumnSegmentImpl) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (Objects.equals(selectColumnSegment.getTable(), columnTable) && Objects.equals(selectPropertyName, propertyName)) {
                    return new EntityPropertyOrder(columnTable, propertyName, selectIndex, asc);
                }
            }
        }
        throw new EasyQueryInvalidOperationException("sharding query order: [" + propertyName + "] not found in select projects");
    }

    /**
     * group 如果不存在select中返回-1
     *
     * @param selectColumns
     * @param columnSegment
     * @param easyQuerySQLExpression
     * @return
     */
    public static PropertyGroup findFirstPropertyGroupNotNull(List<SQLSegment> selectColumns, ColumnSegmentImpl columnSegment, EntityQuerySQLExpression easyQuerySQLExpression) {
        TableAvailable columnTable = columnSegment.getTable();
        String propertyName = columnSegment.getPropertyName();
        int selectIndex = -1;
        for (SQLSegment selectColumn : selectColumns) {
            selectIndex++;
            boolean aggregateColumn = EasySQLSegmentUtil.isAggregateColumn(selectColumn);
            if (!aggregateColumn) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (Objects.equals(selectColumnSegment.getTable(), columnTable) && Objects.equals(selectPropertyName, propertyName)) {
                    return new EntityPropertyGroup(columnTable, propertyName, selectIndex);
                }
            }
        }
        return new EntityPropertyGroup(columnTable, propertyName, -1);
    }
    public static PropertyGroup findFirstPropertyGroupNotNull(List<SQLSegment> selectColumns, Column2Segment columnSegment, EntityQuerySQLExpression easyQuerySQLExpression) {
        TableAvailable columnTable = columnSegment.getTable();
        String propertyName = columnSegment.getColumnMetadata().getPropertyName();
        int selectIndex = -1;
        for (SQLSegment selectColumn : selectColumns) {
            selectIndex++;
            boolean aggregateColumn = EasySQLSegmentUtil.isAggregateColumn(selectColumn);
            if (!aggregateColumn) {
                Column2Segment selectColumnSegment = (Column2Segment) selectColumn;
                String selectPropertyName = selectColumnSegment.getColumnMetadata().getPropertyName();
                if (Objects.equals(selectColumnSegment.getTable(), columnTable) && Objects.equals(selectPropertyName, propertyName)) {
                    return new EntityPropertyGroup(columnTable, propertyName, selectIndex);
                }
            }
        }
        return new EntityPropertyGroup(columnTable, propertyName, -1);
    }


    public static boolean isGroupByAndOrderByStartsWith(List<SQLSegment> groupBy, List<SQLSegment> orderBy) {

        if (EasyCollectionUtil.isEmpty(groupBy)) {
            return false;
        }
        if (EasyCollectionUtil.isNotEmpty(orderBy)) {
            return true;
        }
        int minSize = Math.min(groupBy.size(), orderBy.size());
        for (int i = 0; i < minSize; i++) {
            SQLSegment groupSQLSegment = groupBy.get(i);
            if (!(groupSQLSegment instanceof GroupByColumnSegment)) {
                return false;
            }
            SQLSegment orderSQLSegment = orderBy.get(i);
            if (!(orderSQLSegment instanceof OrderBySegment)) {
                return false;
            }
            GroupByColumnSegment groupColumnSegment = (GroupByColumnSegment) groupSQLSegment;
            OrderBySegment orderColumnSegment = (OrderBySegment) orderSQLSegment;
            if (!Objects.equals(groupColumnSegment.getTable(), orderColumnSegment.getTable())) {
                return false;
            }
            if (!Objects.equals(groupColumnSegment.getPropertyName(), orderColumnSegment.getPropertyName())) {
                return false;
            }
        }
        return true;
    }

    public static boolean processGroup(EntityQuerySQLExpression easyQuerySQLExpression) {
        return EasySQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getGroup()) || hasAggregateSelect(easyQuerySQLExpression);
    }

    private static boolean hasAggregateSelect(EntityQuerySQLExpression easyQuerySQLExpression) {
        if (EasySQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getProjects())) {
            return ((ProjectSQLBuilderSegment) easyQuerySQLExpression.getProjects()).hasAggregateColumns();
        }
        return false;
    }

    public static ConnectionModeEnum getActualConnectionMode(boolean isSerialExecute, int maxShardingQueryLimit, int groupUnitSize, ConnectionModeEnum connectionMode) {
        if (isSerialExecute) {
            return ConnectionModeEnum.MEMORY_STRICTLY;
        }
        if (Objects.equals(ConnectionModeEnum.SYSTEM_AUTO, connectionMode)) {
            if (maxShardingQueryLimit >= groupUnitSize) {
                return ConnectionModeEnum.MEMORY_STRICTLY;
            }
            return ConnectionModeEnum.CONNECTION_STRICTLY;
        } else {
            return connectionMode;
        }
    }

    public static int getMaxShardingQueryLimit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SequenceParseResult sequenceParseResult, EasyQueryOption easyQueryOption) {
        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        Integer maxShardingQueryLimitOrNull = expressionContext.getMaxShardingQueryLimitOrNull();
        if (maxShardingQueryLimitOrNull != null) {
            return maxShardingQueryLimitOrNull;
        } else {
            if (easyQueryOption.getShardingQueryInTransaction() == ShardingQueryInTransactionEnum.SERIALIZABLE) {
                ConnectionManager connectionManager = expressionContext.getRuntimeContext().getConnectionManager();
                if(connectionManager.currentThreadInTransaction()){
                    return 1;
                }
            }
        }
        if (sequenceParseResult != null) {
            if (sequenceParseResult.getConnectionsLimit() > 0) {
                return sequenceParseResult.getConnectionsLimit();
            }
        }
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getQueryConfiguration().getEasyQueryOption().getMaxShardingQueryLimit();
    }

    public static ConnectionModeEnum getConnectionMode(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SequenceParseResult sequenceParseResult) {
        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        ConnectionModeEnum connectionModeOrNull = expressionContext.getConnectionModeOrNull();
        if (connectionModeOrNull != null) {
            return connectionModeOrNull;
        }
        if (sequenceParseResult != null) {
            if (sequenceParseResult.getConnectionMode() != null) {
                return sequenceParseResult.getConnectionMode();
            }
        }
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getQueryConfiguration().getEasyQueryOption().getConnectionMode();
    }

    public static List<RewriteRouteUnit> getSequenceCountRewriteRouteUnits(QueryPrepareParseResult queryPrepareParseResult, RouteContext routeContext, List<Long> countResult) {

        SequenceParseResult sequenceParseResult = queryPrepareParseResult.getSequenceParseResult();
        boolean reverse = sequenceParseResult.isReverse();
        boolean asc = sequenceParseResult.getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareAscMethods(ExecuteMethodEnum.COUNT);
        boolean countResultReverse = reverse == asc;
        List<RouteUnit> routeUnits = routeContext.getShardingRouteResult().getRouteUnits();
        ArrayList<RewriteRouteUnit> rewriteRouteUnits = new ArrayList<>(routeUnits.size());
        int routeUnitsSize = routeUnits.size();
        int countSize = countResult.size();
        for (int i = 0; i < routeUnitsSize; i++) {
            RouteUnit routeUnit = routeUnits.get(i);
            int countResultIndex = countResultReverse ? countSize - 1 - i : i;
            Long total = countResult.size() > countResultIndex ? countResult.get(countResultIndex) : null;
            if (total == null) {
                rewriteRouteUnits.add(new DefaultRewriteRouteUnit(routeUnit));
            } else {
                if (total < 0L) {
                    rewriteRouteUnits.add(new DefaultRewriteRouteUnit(routeUnit));
                }
            }
        }
        return rewriteRouteUnits;
    }

    public static List<RewriteRouteUnit> getSequencePaginationRewriteRouteUnits(QueryPrepareParseResult queryPrepareParseResult, RouteContext routeContext, List<Long> countResult) {

        SequenceParseResult sequenceParseResult = queryPrepareParseResult.getSequenceParseResult();
        boolean reverse = sequenceParseResult.isReverse();
        boolean asc = sequenceParseResult.getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareAscMethods(ExecuteMethodEnum.COUNT);
        boolean countResultReverse = reverse == asc;
        List<RouteUnit> routeUnits = routeContext.getShardingRouteResult().getRouteUnits();
        ArrayList<RewriteRouteUnit> rewriteRouteUnits = new ArrayList<>(routeUnits.size());
        long offset = queryPrepareParseResult.getOriginalOffset();
        long rows = queryPrepareParseResult.getOriginalRows();
        long currentOffset = offset;
        long currentRows = rows;
        boolean stopSkip = false;
        boolean needBreak = false;
        int countSize = countResult.size();
        for (int i = 0; i < countSize; i++) {
            int countResultIndex = countResultReverse ? countSize - 1 - i : i;
            Long total = countResult.get(countResultIndex);
            RouteUnit routeUnit = routeUnits.get(i);
            if (!stopSkip) {
                if (total > currentOffset) {
                    stopSkip = true;
                } else {
                    currentOffset = currentOffset - total;
                    continue;
                }
            }
            long currentRealOffset = currentOffset;
            long currentRealRows = total - currentOffset;
            if (currentOffset != 0L)
                currentOffset = 0;

            if (currentRows <= currentRealRows) {
                currentRealRows = currentRows;
                needBreak = true;
            } else {
                currentRows = currentRows - currentRealRows;
            }
            rewriteRouteUnits.add(new SequencePaginationRewriteRouteUnit(currentRealOffset, currentRealRows, routeUnit));
            if (needBreak) {
                break;
            }
        }
        return rewriteRouteUnits;
    }

    //
//    public static int parseStreamMergeContextMergeBehavior(StreamMergeContext streamMergeContext) {
//
//        int mergeBehavior = MergeBehaviorEnum.DEFAULT.getCode();
//
//        if (streamMergeContext.isQuery() && streamMergeContext.isSharding()) {
//
//            switch (streamMergeContext.getExecutorContext().getExecuteMethod()) {
//                case ALL:
//                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ALL.getCode());
//                    break;
//                case ANY:
//                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ANY.getCode());
//                    break;
//                case COUNT:
//                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.COUNT.getCode());
//                    break;
//            }
//            if (processGroup(streamMergeContext)) {
//                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.GROUP.getCode());
//                if (streamMergeContext.isStartsWithGroupByInOrderBy()) {
//                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.STREAM_GROUP.getCode());
//                }
//            }
//            if (EasyCollectionUtil.isNotEmpty(streamMergeContext.getOrders())) {
//                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode());
//            }
//            if (streamMergeContext.isPaginationQuery()) {
//                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.PAGINATION.getCode());
//
//                ShardingQueryCountManager shardingQueryCountManager = streamMergeContext.getRuntimeContext().getShardingQueryCountManager();
//                if (shardingQueryCountManager.isBegin()) {
//                    //顺序分页并且count也是顺序查询
//                    if (streamMergeContext.isSeqQuery() &&
//                            streamMergeContext.getSequenceParseResult().getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareMethods(ExecuteMethodEnum.COUNT)
//                            && streamMergeContext.getExecutionUnits().size() == shardingQueryCountManager.getCountResult().size()) {
//                        mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.SEQUENCE_PAGINATION.getCode());
//                    }
//                    if (BitwiseUtil.hasBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode())) {
//                        PropertyOrder propertyOrder = EasyCollectionUtil.first(streamMergeContext.getOrders());
//                        ShardingInitConfig shardingInitConfig = propertyOrder.getTable().getEntityTable().getEntityMetadata().getShardingInitConfig();
//                        if (shardingInitConfig.isReverse()) {
//                            List<SequenceCountNode> countResult = shardingQueryCountManager.getCountResult();
//                            long total = EasyCollectionUtil.sumLong(countResult, SequenceCountNode::getTotal);
//                            if(shardingInitConfig.getReverseFactor()*total>shardingInitConfig.getMinReverseTotal()){
//                                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.REVERSE_PAGINATION.getCode());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return mergeBehavior;
//    }
    public static int parseMergeBehavior(PrepareParseResult prepareParseResult, EntitySQLExpression easyEntitySQLExpression, RouteContext routeContext) {

        int mergeBehavior = MergeBehaviorEnum.DEFAULT.getCode();
        ExecutorContext executorContext = prepareParseResult.getExecutorContext();
        if (executorContext.isQuery() && EasyCollectionUtil.isNotEmpty(prepareParseResult.getTableParseDescriptor().getTables())) {
            QueryPrepareParseResult queryPrepareParseResult = (QueryPrepareParseResult) prepareParseResult;
            EntityQuerySQLExpression easyQuerySQLExpression = (EntityQuerySQLExpression) easyEntitySQLExpression;
            switch (executorContext.getExecuteMethod()) {
//                case ALL:
//                    mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ALL.getCode());
//                    break;
                case ANY:
                    mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ANY.getCode());
                    break;
                case COUNT: {
                    mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.COUNT.getCode());
                    if (queryPrepareParseResult.isSeqQuery()) {
                        ShardingQueryCountManager shardingQueryCountManager = executorContext.getRuntimeContext().getShardingQueryCountManager();
                        if (shardingQueryCountManager.isBegin()) {
                            mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.SEQUENCE_COUNT.getCode());
                        }
                    }
                }
                break;
            }
            if (processGroup(easyQuerySQLExpression)) {
                mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.GROUP.getCode());
                if (queryPrepareParseResult.isStartsWithGroupByInOrderBy()) {
                    mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.STREAM_GROUP.getCode());
                }
            }
            if (EasySQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getOrder())) {
                mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode());
            }
            if (queryPrepareParseResult.isPaginationQuery()) {
                mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.PAGINATION.getCode());

                ShardingQueryCountManager shardingQueryCountManager = executorContext.getRuntimeContext().getShardingQueryCountManager();
                if (shardingQueryCountManager.isBegin()) {
                    //顺序分页并且count也是顺序查询
                    if (queryPrepareParseResult.isSeqQuery() &&
                            queryPrepareParseResult.getSequenceParseResult().getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareMethods(ExecuteMethodEnum.COUNT)
                            && routeContext.getShardingRouteResult().getRouteUnits().size() == shardingQueryCountManager.getCountResult().size()) {
                        mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.SEQUENCE_PAGINATION.getCode());
                    }
                    if (EasyBitwiseUtil.hasBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode())) {
                        OrderBySegment firstOrder = (OrderBySegment) EasyCollectionUtil.first(easyQuerySQLExpression.getOrder().getSQLSegments());
                        ShardingInitConfig shardingInitConfig = firstOrder.getTable().getEntityMetadata().getShardingInitConfig();
                        if (shardingInitConfig.isReverse()) {
                            List<Long> countResult = shardingQueryCountManager.getCountResult();
                            long total = EasyCollectionUtil.sumLong(countResult, o -> o);
                            if (shardingInitConfig.getReverseFactor() * total > shardingInitConfig.getMinReverseTotal()) {
                                mergeBehavior = EasyBitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.REVERSE_PAGINATION.getCode());
                            }
                        }
                    }
                }
            }
        }
        return mergeBehavior;
    }
}
