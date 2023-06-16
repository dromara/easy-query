package com.easy.query.core.sharding.context;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.enums.MergeBehaviorEnum;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyGroup;
import com.easy.query.core.basic.jdbc.executor.internal.merge.segment.PropertyOrder;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyShardingUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

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
    protected final EntityQuerySQLExpression querySQLExpression;
    protected volatile boolean terminated = false;

    public ShardingQueryEasyStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext, EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        super(executorContext, executionContext, easyQueryPrepareParseResult);
        this.easyQueryPrepareParseResult = easyQueryPrepareParseResult;
        this.querySQLExpression = easyQueryPrepareParseResult.getEntityPredicateSQLExpression();
        this.orders = getOrders(querySQLExpression);
        this.groups = getGroups(querySQLExpression);
    }

    private List<PropertyOrder> getOrders(EntityQuerySQLExpression querySQLExpression) {
        if (isShardingMerge() && EasySQLSegmentUtil.isNotEmpty(querySQLExpression.getOrder())) {
            List<PropertyOrder> orders = new ArrayList<>();
            SQLBuilderSegment projects = querySQLExpression.getProjects();
            for (SQLSegment sqlSegment : querySQLExpression.getOrder().getSQLSegments()) {
                if (sqlSegment instanceof OrderBySegment) {
                    OrderBySegment orderColumnSegment = (OrderBySegment) sqlSegment;

                    PropertyOrder propertyOrder = EasyShardingUtil.findFirstPropertyOrderNotNull(projects.getSQLSegments(), orderColumnSegment, querySQLExpression);
                    orders.add(propertyOrder);
                }
            }
            return orders;
        } else {
            return EMPTY_SQL_ORDERS;
        }
    }
    private List<PropertyGroup> getGroups(EntityQuerySQLExpression querySQLExpression) {
        if (isShardingMerge() && EasySQLSegmentUtil.isNotEmpty(querySQLExpression.getGroup())) {
            List<PropertyGroup> groups = new ArrayList<>();
            SQLBuilderSegment projects = querySQLExpression.getProjects();
            for (SQLSegment sqlSegment : querySQLExpression.getGroup().getSQLSegments()) {
                if (sqlSegment instanceof ColumnSegmentImpl) {
                    ColumnSegmentImpl columnSegment = (ColumnSegmentImpl) sqlSegment;

                    PropertyGroup propertyGroup = EasyShardingUtil.findFirstPropertyGroupNotNull(projects.getSQLSegments(), columnSegment, querySQLExpression);
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
        return EasyBitwiseUtil.hasBit(executionContext.getMergeBehavior(),mergeBehavior.getCode());
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
        return querySQLExpression.getOffset();
    }

    @Override
    public long getRewriteRows() {
        return querySQLExpression.getRows();
    }

    @Override
    public SQLBuilderSegment getSelectColumns() {
        return querySQLExpression.getProjects();
    }


    @Override
    public SQLBuilderSegment getGroupColumns() {
        return querySQLExpression.getGroup();
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
