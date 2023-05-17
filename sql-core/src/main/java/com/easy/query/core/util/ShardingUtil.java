package com.easy.query.core.util;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.metadata.ShardingInitConfig;
import com.easy.query.core.sharding.manager.SequenceCountNode;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.EntityPropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.EntityPropertyOrder;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.sharding.rewrite.DefaultRewriteRouteUnit;
import com.easy.query.core.sharding.rewrite.RewriteRouteUnit;
import com.easy.query.core.sharding.rewrite.SequencePaginationRewriteRouteUnit;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RoutePredicateDiscover;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/19 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingUtil {

    public static <T> RoutePredicateExpression<T> getRoutePredicateExpression(PrepareParseResult prepareParseResult, TableAvailable table,
                                                                       RouteRuleFilter<T> routeRuleFilter, boolean shardingTableRoute) {

        RoutePredicateDiscover<T> routePredicateDiscover = new RoutePredicateDiscover<T>(prepareParseResult, table, routeRuleFilter, shardingTableRoute);
        return routePredicateDiscover.getRouteParseExpression();
    }

    public static PropertyOrder findFirstPropertyOrderNotNull(List<SQLSegment> selectColumns, OrderColumnSegmentImpl orderColumnSegment, QuerySQLExpression easyQuerySQLExpression) {
        int tableIndex = orderColumnSegment.getTable().getIndex();
        String propertyName = orderColumnSegment.getPropertyName();
        boolean asc = orderColumnSegment.isAsc();
        int selectIndex = -1;
        for (SQLSegment selectColumn : selectColumns) {
            selectIndex++;
            if (selectColumn instanceof ColumnSegmentImpl) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    TableSQLExpression table = easyQuerySQLExpression.getTable(tableIndex);
                    return new EntityPropertyOrder(table, propertyName, selectIndex, asc);
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
    public static PropertyGroup findFirstPropertyGroupNotNull(List<SQLSegment> selectColumns, ColumnSegmentImpl columnSegment, QuerySQLExpression easyQuerySQLExpression) {
        int tableIndex = columnSegment.getTable().getIndex();
        String propertyName = columnSegment.getPropertyName();
        int selectIndex = -1;
        for (SQLSegment selectColumn : selectColumns) {
            selectIndex++;
            if (!(selectColumn instanceof AggregationColumnSegment)) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    TableSQLExpression table = easyQuerySQLExpression.getTable(tableIndex);
                    return new EntityPropertyGroup(table, propertyName, selectIndex);
                }
            }
        }
        TableSQLExpression table = easyQuerySQLExpression.getTable(tableIndex);
        return new EntityPropertyGroup(table, propertyName, -1);
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
            if (!(orderSQLSegment instanceof OrderByColumnSegment)) {
                return false;
            }
            GroupByColumnSegment groupColumnSegment = (GroupByColumnSegment) groupSQLSegment;
            OrderByColumnSegment orderColumnSegment = (OrderByColumnSegment) orderSQLSegment;
            if (groupColumnSegment.getTable().getIndex() != orderColumnSegment.getTable().getIndex()) {
                return false;
            }
            if (!Objects.equals(groupColumnSegment.getPropertyName(), orderColumnSegment.getPropertyName())) {
                return false;
            }
        }
        return true;
    }

    public static boolean processGroup(QuerySQLExpression easyQuerySQLExpression) {
        return SQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getGroup()) || hasAggregateSelect(easyQuerySQLExpression);
    }

    private static boolean hasAggregateSelect(QuerySQLExpression easyQuerySQLExpression) {
        if (SQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getProjects())) {
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

    public static int getMaxShardingQueryLimit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SequenceParseResult sequenceParseResult) {
        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        Integer maxShardingQueryLimitOrNull = expressionContext.getMaxShardingQueryLimitOrNull();
        if (maxShardingQueryLimitOrNull != null) {
            return maxShardingQueryLimitOrNull;
        }
        if (sequenceParseResult != null) {
            if (sequenceParseResult.getConnectionsLimit() > 0) {
                return sequenceParseResult.getConnectionsLimit();
            }
        }
        EasyQueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getMaxShardingQueryLimit();
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
        EasyQueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getConnectionMode();
    }
    public static List<RewriteRouteUnit> getSequenceCountRewriteRouteUnits(QueryPrepareParseResult queryPrepareParseResult, RouteContext routeContext, List<SequenceCountNode> countResult){

        SequenceParseResult sequenceParseResult = queryPrepareParseResult.getSequenceParseResult();
        boolean reverse = sequenceParseResult.isReverse();
        boolean asc = sequenceParseResult.getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareAscMethods(ExecuteMethodEnum.COUNT);
        boolean countResultReverse = reverse == asc;
        List<RouteUnit> routeUnits = routeContext.getShardingRouteResult().getRouteUnits();
        ArrayList<RewriteRouteUnit> rewriteRouteUnits = new ArrayList<>(routeUnits.size());
        int routeUnitsSize =routeUnits.size();
        int countSize = countResult.size();
        for (int i = 0; i < routeUnitsSize; i++) {
            RouteUnit routeUnit = routeUnits.get(i);
            int countResultIndex = countResultReverse ? countSize - 1 - i : i;
            SequenceCountNode SequenceCountNode =countResult.size()>countResultIndex? countResult.get(countResultIndex):null;
            if(SequenceCountNode==null){
                rewriteRouteUnits.add(new DefaultRewriteRouteUnit(routeUnit));
            }else{
                if(SequenceCountNode.getTotal()<0L){
                    rewriteRouteUnits.add(new DefaultRewriteRouteUnit(routeUnit));
                }
            }
        }
        return rewriteRouteUnits;
    }

    public static List<RewriteRouteUnit> getSequencePaginationRewriteRouteUnits(QueryPrepareParseResult queryPrepareParseResult, RouteContext routeContext, List<SequenceCountNode> countResult) {

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
            SequenceCountNode SequenceCountNode = countResult.get(countResultIndex);
            RouteUnit routeUnit = routeUnits.get(i);
            if (!stopSkip) {
                if (SequenceCountNode.getTotal() > currentOffset) {
                    stopSkip = true;
                } else {
                    currentOffset = currentOffset - SequenceCountNode.getTotal();
                    continue;
                }
            }
            long currentRealOffset = currentOffset;
            long currentRealRows = SequenceCountNode.getTotal() - currentOffset;
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
        if (executorContext.isQuery() && EasyCollectionUtil.isNotEmpty(prepareParseResult.getShardingTables())) {
            QueryPrepareParseResult queryPrepareParseResult = (QueryPrepareParseResult) prepareParseResult;
            QuerySQLExpression easyQuerySQLExpression = (QuerySQLExpression) easyEntitySQLExpression;
            switch (executorContext.getExecuteMethod()) {
                case ALL:
                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ALL.getCode());
                    break;
                case ANY:
                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ANY.getCode());
                    break;
                case COUNT: {
                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.COUNT.getCode());
                    if (queryPrepareParseResult.isSeqQuery()) {
                        ShardingQueryCountManager shardingQueryCountManager = executorContext.getRuntimeContext().getShardingQueryCountManager();
                        if (shardingQueryCountManager.isBegin()){
                            mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.SEQUENCE_COUNT.getCode());
                        }
                    }
                }
                break;
            }
            if (processGroup(easyQuerySQLExpression)) {
                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.GROUP.getCode());
                if (queryPrepareParseResult.isStartsWithGroupByInOrderBy()) {
                    mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.STREAM_GROUP.getCode());
                }
            }
            if (SQLSegmentUtil.isNotEmpty(easyQuerySQLExpression.getOrder())) {
                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode());
            }
            if (queryPrepareParseResult.isPaginationQuery()) {
                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.PAGINATION.getCode());

                ShardingQueryCountManager shardingQueryCountManager = executorContext.getRuntimeContext().getShardingQueryCountManager();
                if (shardingQueryCountManager.isBegin()) {
                    //顺序分页并且count也是顺序查询
                    if (queryPrepareParseResult.isSeqQuery() &&
                            queryPrepareParseResult.getSequenceParseResult().getTable().getEntityMetadata().getShardingInitConfig().getShardingSequenceConfig().hasCompareMethods(ExecuteMethodEnum.COUNT)
                            && routeContext.getShardingRouteResult().getRouteUnits().size() == shardingQueryCountManager.getCountResult().size()) {
                        mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.SEQUENCE_PAGINATION.getCode());
                    }
                    if (BitwiseUtil.hasBit(mergeBehavior, MergeBehaviorEnum.ORDER.getCode())) {
                        OrderByColumnSegment firstOrder = (OrderByColumnSegment) EasyCollectionUtil.first(easyQuerySQLExpression.getOrder().getSQLSegments());
                        ShardingInitConfig shardingInitConfig = firstOrder.getTable().getEntityMetadata().getShardingInitConfig();
                        if (shardingInitConfig.isReverse()) {
                            List<SequenceCountNode> countResult = shardingQueryCountManager.getCountResult();
                            long total = EasyCollectionUtil.sumLong(countResult, SequenceCountNode::getTotal);
                            if (shardingInitConfig.getReverseFactor() * total > shardingInitConfig.getMinReverseTotal()) {
                                mergeBehavior = BitwiseUtil.addBit(mergeBehavior, MergeBehaviorEnum.REVERSE_PAGINATION.getCode());
                            }
                        }
                    }
                }
            }
        }
        return mergeBehavior;
    }
}
