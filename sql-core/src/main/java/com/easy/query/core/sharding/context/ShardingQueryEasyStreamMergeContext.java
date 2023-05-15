package com.easy.query.core.sharding.context;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.util.BitwiseUtil;
import com.easy.query.core.util.ShardingUtil;
import com.easy.query.core.util.SqlSegmentUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/27 15:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingQueryEasyStreamMergeContext extends EntityStreamMergeContext {
    protected static List<PropertyOrder> EMPTY_SQL_ORDERS = Collections.emptyList();
    protected static List<PropertyGroup> EMPTY_SQL_GROUPS =Collections.emptyList();
    private final EasyQueryPrepareParseResult easyQueryPrepareParseResult;
    protected final List<PropertyOrder> orders;
    protected final List<PropertyGroup> groups;
    protected final EasyQuerySqlExpression easyQuerySqlExpression;
    protected volatile boolean terminated = false;

    public ShardingQueryEasyStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext, EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        super(executorContext, executionContext, easyQueryPrepareParseResult);
        this.easyQueryPrepareParseResult = easyQueryPrepareParseResult;
        this.easyQuerySqlExpression = easyQueryPrepareParseResult.getEasyEntityPredicateSqlExpression();
        this.orders = getOrders(easyQuerySqlExpression);
        this.groups = getGroups(easyQuerySqlExpression);
    }

    private List<PropertyOrder> getOrders(EasyQuerySqlExpression easyQuerySqlExpression) {
        if (isShardingMerge() && SqlSegmentUtil.isNotEmpty(easyQuerySqlExpression.getOrder())) {
            List<PropertyOrder> orders = new ArrayList<>();
            SqlBuilderSegment projects = easyQuerySqlExpression.getProjects();
            for (SqlSegment sqlSegment : easyQuerySqlExpression.getOrder().getSqlSegments()) {
                if (sqlSegment instanceof OrderColumnSegmentImpl) {
                    OrderColumnSegmentImpl orderColumnSegment = (OrderColumnSegmentImpl) sqlSegment;

                    PropertyOrder propertyOrder = ShardingUtil.findFirstPropertyOrderNotNull(projects.getSqlSegments(), orderColumnSegment, easyQuerySqlExpression);
                    orders.add(propertyOrder);
                }
            }
            return orders;
        } else {
            return EMPTY_SQL_ORDERS;
        }
    }
    private List<PropertyGroup> getGroups(EasyQuerySqlExpression easyQuerySqlExpression) {
        if (isShardingMerge() && SqlSegmentUtil.isNotEmpty(easyQuerySqlExpression.getGroup())) {
            List<PropertyGroup> groups = new ArrayList<>();
            SqlBuilderSegment projects = easyQuerySqlExpression.getProjects();
            for (SqlSegment sqlSegment : easyQuerySqlExpression.getGroup().getSqlSegments()) {
                if (sqlSegment instanceof ColumnSegmentImpl) {
                    ColumnSegmentImpl columnSegment = (ColumnSegmentImpl) sqlSegment;

                    PropertyGroup propertyGroup = ShardingUtil.findFirstPropertyGroupNotNull(projects.getSqlSegments(), columnSegment, easyQuerySqlExpression);
                    groups.add(propertyGroup);
                }
            }
            return groups;
        } else {
            return EMPTY_SQL_GROUPS;
        }
    }

    @Override
    public boolean hasBehavior(MergeBehaviorEnum mergeBehavior) {
        return BitwiseUtil.hasBit(executionContext.getMergeBehavior(),mergeBehavior.getCode());
    }

    @Override
    public List<PropertyOrder> getOrders() {
        return this.orders;
    }

    @Override
    public List<PropertyGroup> getGroups() {
        return this.groups;
    }

    @Override
    public boolean isPaginationQuery() {
        return easyQueryPrepareParseResult.getOriginalOffset() > 0 || easyQueryPrepareParseResult.getOriginalRows() > 0;
    }

    @Override
    public long getOriginalOffset() {
        return easyQueryPrepareParseResult.getOriginalOffset();
    }

    @Override
    public long getOriginalRows() {
        return easyQueryPrepareParseResult.getOriginalRows();
    }

    @Override
    public long getMergeOffset() {
        if(isReverseMerge()){
            return this.getRewriteRows()-this.getOriginalRows();
        }
        return getOriginalOffset();
    }

    @Override
    public long getMergeRows() {
        return getOriginalRows();
    }

    @Override
    public long getRewriteOffset() {
        return easyQuerySqlExpression.getOffset();
    }

    @Override
    public long getRewriteRows() {
        return easyQuerySqlExpression.getRows();
    }

    @Override
    public SqlBuilderSegment getSelectColumns() {
        return easyQuerySqlExpression.getProjects();
    }


    @Override
    public SqlBuilderSegment getGroupColumns() {
        return easyQuerySqlExpression.getGroup();
    }


    @Override
    public boolean isSeqQuery() {
        return executionContext.isSequenceQuery()&&easyQueryPrepareParseResult.getSequenceParseResult()!=null;
    }

    @Override
    public boolean isReverseMerge() {
        return this.executionContext.isReverseMerge();
    }

    @Override
    public ConnectionModeEnum getConnectionMode() {
        return easyQueryPrepareParseResult.getConnectionMode();
    }

    @Override
    public int getMaxShardingQueryLimit() {
        return easyQueryPrepareParseResult.getMaxShardingQueryLimit();
    }

    @Override
    public void terminatedBreak() {
        this.terminated=true;
    }

    @Override
    public boolean isTerminated() {
        return terminated;
    }
}
