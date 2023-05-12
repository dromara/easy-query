package com.easy.query.core.util;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.SequenceParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.context.StreamMergeContext;
import com.easy.query.core.sharding.merge.segment.EntityPropertyGroup;
import com.easy.query.core.sharding.merge.segment.EntityPropertyOrder;
import com.easy.query.core.sharding.merge.segment.PropertyGroup;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
import com.easy.query.core.sharding.route.RoutePredicateDiscover;
import com.easy.query.core.sharding.route.RoutePredicateExpression;
import com.easy.query.core.sharding.rule.RouteRuleFilter;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/19 08:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingUtil {

    public static RoutePredicateExpression getRoutePredicateExpression(PrepareParseResult prepareParseResult, TableAvailable table,
                                                                       RouteRuleFilter routeRuleFilter, boolean shardingTableRoute) {

        RoutePredicateDiscover routePredicateDiscover = new RoutePredicateDiscover(prepareParseResult, table, routeRuleFilter, shardingTableRoute);
        return routePredicateDiscover.getRouteParseExpression();
    }

    public static PropertyOrder findFirstPropertyOrderNotNull(List<SqlSegment> selectColumns, OrderColumnSegmentImpl orderColumnSegment, EasyQuerySqlExpression easyQuerySqlExpression) {
        int tableIndex = orderColumnSegment.getTable().getIndex();
        String propertyName = orderColumnSegment.getPropertyName();
        boolean asc = orderColumnSegment.isAsc();
        int selectIndex = -1;
        for (SqlSegment selectColumn : selectColumns) {
            selectIndex++;
            if (selectColumn instanceof ColumnSegmentImpl) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
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
     * @param easyQuerySqlExpression
     * @return
     */
    public static PropertyGroup findFirstPropertyGroupNotNull(List<SqlSegment> selectColumns, ColumnSegmentImpl columnSegment, EasyQuerySqlExpression easyQuerySqlExpression) {
        int tableIndex = columnSegment.getTable().getIndex();
        String propertyName = columnSegment.getPropertyName();
        int selectIndex = -1;
        for (SqlSegment selectColumn : selectColumns) {
            selectIndex++;
            if (!(selectColumn instanceof AggregationColumnSegment)) {
                ColumnSegmentImpl selectColumnSegment = (ColumnSegmentImpl) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
                    return new EntityPropertyGroup(table, propertyName, selectIndex);
                }
            }
        }
        EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
        return new EntityPropertyGroup(table, propertyName, -1);
    }


    public static boolean isGroupByAndOrderByStartsWith(List<SqlSegment> groupBy, List<SqlSegment> orderBy) {

        if (EasyCollectionUtil.isEmpty(groupBy)) {
            return false;
        }
        if (EasyCollectionUtil.isNotEmpty(orderBy)) {
            return true;
        }
        int minSize = Math.min(groupBy.size(), orderBy.size());
        for (int i = 0; i < minSize; i++) {
            SqlSegment groupSqlSegment = groupBy.get(i);
            if (!(groupSqlSegment instanceof GroupByColumnSegment)) {
                return false;
            }
            SqlSegment orderSqlSegment = orderBy.get(i);
            if (!(orderSqlSegment instanceof OrderByColumnSegment)) {
                return false;
            }
            GroupByColumnSegment groupColumnSegment = (GroupByColumnSegment) groupSqlSegment;
            OrderByColumnSegment orderColumnSegment = (OrderByColumnSegment) orderSqlSegment;
            if (groupColumnSegment.getTable().getIndex() != orderColumnSegment.getTable().getIndex()) {
                return false;
            }
            if (!Objects.equals(groupColumnSegment.getPropertyName(), orderColumnSegment.getPropertyName())) {
                return false;
            }
        }
        return true;
    }

    public static boolean processGroup(StreamMergeContext streamMergeContext){
        return streamMergeContext.hasGroupQuery()||hasAggregateSelect(streamMergeContext);
    }
    private static boolean hasAggregateSelect(StreamMergeContext streamMergeContext){
        if(SqlSegmentUtil.isNotEmpty(streamMergeContext.getSelectColumns())){
            return ((ProjectSqlBuilderSegment)streamMergeContext.getSelectColumns()).hasAggregateColumns();
        }
        return false;
    }

    public static ConnectionModeEnum getActualConnectionMode(boolean isSerialExecute,int maxShardingQueryLimit,int groupUnitSize,ConnectionModeEnum connectionMode){
        if(isSerialExecute){
            return ConnectionModeEnum.MEMORY_STRICTLY;
        }
        if(Objects.equals(ConnectionModeEnum.SYSTEM_AUTO,connectionMode)){
            if(maxShardingQueryLimit>=groupUnitSize){
                return ConnectionModeEnum.MEMORY_STRICTLY;
            }
            return ConnectionModeEnum.CONNECTION_STRICTLY;
        }else{
            return connectionMode;
        }
    }

    public static int getMaxShardingQueryLimit(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SequenceParseResult sequenceParseResult){
        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        Integer maxShardingQueryLimitOrNull = expressionContext.getMaxShardingQueryLimitOrNull();
        if(maxShardingQueryLimitOrNull!=null){
            return maxShardingQueryLimitOrNull;
        }
        if(sequenceParseResult!=null){
            if(sequenceParseResult.getConnectionsLimit()>0){
                return sequenceParseResult.getConnectionsLimit();
            }
        }
        EasyQueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getMaxShardingQueryLimit();
    }
    public static ConnectionModeEnum getConnectionMode(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SequenceParseResult sequenceParseResult){
        ExpressionContext expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        ConnectionModeEnum connectionModeOrNull = expressionContext.getConnectionModeOrNull();
        if(connectionModeOrNull!=null){
            return connectionModeOrNull;
        }
        if(sequenceParseResult!=null){
            if(sequenceParseResult.getConnectionMode()!=null){
                return sequenceParseResult.getConnectionMode();
            }
        }
        EasyQueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        return runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getConnectionMode();
    }
}
