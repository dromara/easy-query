package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.segment.AggregationColumnSegment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegmentImpl;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.OrderBySqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.sharding.merge.result.aggregation.AggregationType;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.ShardingUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2023/4/20 14:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRewriteContextFactory implements RewriteContextFactory {
    @Override
    public void rewriteShardingExpression(PrepareParseResult prepareParseResult) {
        if (prepareParseResult instanceof QueryPrepareParseResult) {
            rewriteShardingQueryExpression((QueryPrepareParseResult) prepareParseResult);
        }
    }

    public void rewriteShardingQueryExpression(QueryPrepareParseResult queryPrepareParseResult) {
        EasyQuerySqlExpression easyQuerySqlExpression = queryPrepareParseResult.getEasyEntityPredicateSqlExpression();
        if (easyQuerySqlExpression.hasLimit()) {
            long rows = easyQuerySqlExpression.getRows();
            long offset = easyQuerySqlExpression.getOffset();
            if (offset > 0) {
                easyQuerySqlExpression.setOffset(0);
            }
            easyQuerySqlExpression.setRows(offset + rows);
        }
        //如果当前表达式存在group 并且
        if (SqlSegmentUtil.isNotEmpty(easyQuerySqlExpression.getGroup())) {

            boolean hasAvg=false;
            //分组重写的如果存在count avg sum那么就会存储
            Map<GroupRewriteStatus,GroupRewriteStatus> groupRewriteStatusMap = new LinkedHashMap<>(easyQuerySqlExpression.getProjects().getSqlSegments().size());
            //group的字段必须要全部存在于select中
            //遍历所有的表达式
            for (SqlSegment groupSqlSegment : easyQuerySqlExpression.getGroup().getSqlSegments()) {
                if (!(groupSqlSegment instanceof ColumnSegment)) {
                    throw new UnsupportedOperationException("sharding rewrite group not implement ColumnSegment:" + ClassUtil.getInstanceSimpleName(groupSqlSegment));
                }
                ColumnSegment groupColumnSegment = (ColumnSegment) groupSqlSegment;

                boolean addToProjection = true;
                for (SqlSegment sqlSegment : easyQuerySqlExpression.getProjects().getSqlSegments()) {
                    if (sqlSegment instanceof AggregationColumnSegment) {
                        AggregationColumnSegment aggregationColumnSegment = (AggregationColumnSegment) sqlSegment;
                        //是否存在avg
                        if(!hasAvg){
                            hasAvg=Objects.equals(AggregationType.AVG,aggregationColumnSegment.getAggregationType());
                        }
                        GroupRewriteStatus groupRewriteStatusKey = new GroupRewriteStatus(aggregationColumnSegment.getTable().getIndex(), aggregationColumnSegment.getPropertyName());
                        GroupRewriteStatus groupRewriteStatus = groupRewriteStatusMap.computeIfAbsent(groupRewriteStatusKey, k->groupRewriteStatusKey);

                        GroupAvgBehaviorEnum groupAvgBehavior = GroupAvgBehaviorEnum.getGroupAvgBehavior(aggregationColumnSegment.getAggregationType());
                        if(groupAvgBehavior!=null){
                            groupRewriteStatus.removeBehavior(groupAvgBehavior);
                        }

                        continue;
                    }
                    if (!(sqlSegment instanceof ColumnSegment)) {
                        throw new UnsupportedOperationException("sharding rewrite projection not implement ColumnSegment:" + ClassUtil.getInstanceSimpleName(sqlSegment));
                    }
                    ColumnSegment columnSegment = (ColumnSegment) sqlSegment;
                    //
                    if (groupColumnSegment.getTable().getIndex() == columnSegment.getTable().getIndex()) {
                        if (Objects.equals(groupColumnSegment.getPropertyName(), columnSegment.getPropertyName())) {
                            if(addToProjection){
                                addToProjection = false;
                            }
                        }
                    }
                }
                if (addToProjection) {
                    easyQuerySqlExpression.getProjects().append(groupColumnSegment.cloneSqlEntitySegment());
                }
            }

            List<SqlSegment> groupSqlSegments = easyQuerySqlExpression.getGroup().getSqlSegments();
            List<SqlSegment> orderSqlSegments = easyQuerySqlExpression.getOrder().getSqlSegments();
            //group by或者order by小的那个是另一个的startsWith即可
            boolean startsWithGroupByAndOrderBy = ShardingUtil.isGroupByAndOrderByStartsWith(groupSqlSegments,orderSqlSegments);
            queryPrepareParseResult.setStartsWithGroupByInOrderBy(startsWithGroupByAndOrderBy);
            //如果是的情况下
            if(startsWithGroupByAndOrderBy){
                int orderBySize = orderSqlSegments.size();
                int groupBySize = groupSqlSegments.size();
                if(orderBySize<groupBySize){
                    for (int i = orderBySize; i < groupBySize; i++) {
                        GroupByColumnSegment groupByColumnSegment = (GroupByColumnSegment)groupSqlSegments.get(i);
                        easyQuerySqlExpression.getOrder().append(groupByColumnSegment.createOrderByColumnSegment(true));
                    }
                }
            }
            //判断sum count avg如果存在avg那么前面两个不能缺
            if(hasAvg){
                for (Map.Entry<GroupRewriteStatus, GroupRewriteStatus> groupRewriteStatusKv: groupRewriteStatusMap.entrySet()) {
                    GroupRewriteStatus rewriteStatusKvKey = groupRewriteStatusKv.getKey();
                    //如果avg还有说明本次group没有avg
                    if(rewriteStatusKvKey.hasBehavior(GroupAvgBehaviorEnum.AVG)){
                        continue;
                    }
                    //如果存在avg那么分片必须要存在count或者sum不然无法计算avg
                    if(rewriteStatusKvKey.hasBehavior(GroupAvgBehaviorEnum.COUNT)&&rewriteStatusKvKey.hasBehavior(GroupAvgBehaviorEnum.SUM)){
                        EasyTableSqlExpression table = easyQuerySqlExpression.getTable(rewriteStatusKvKey.getTableIndex());
                        easyQuerySqlExpression.getProjects().append(new FuncColumnSegmentImpl(table.getEntityTable(),rewriteStatusKvKey.getPropertyName(), easyQuerySqlExpression.getRuntimeContext(), EasyAggregate.COUNT,rewriteStatusKvKey.getPropertyName()+"RewriteCount"));
                    }
                }
            }
        }
    }
}
