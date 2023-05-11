package com.easy.query.core.sharding.merge.context;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.segment.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.merge.segment.PropertyGroup;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
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
    protected final boolean hasGroup;
    protected volatile boolean terminated = false;
    protected long offset;
    protected long rows;

    public ShardingQueryEasyStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext, EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        super(executorContext, executionContext, easyQueryPrepareParseResult);
        this.easyQueryPrepareParseResult = easyQueryPrepareParseResult;
        this.easyQuerySqlExpression = easyQueryPrepareParseResult.getEasyEntityPredicateSqlExpression();
        this.orders = getOrders(easyQuerySqlExpression);
        this.offset = easyQuerySqlExpression.getOffset();
        this.rows = easyQuerySqlExpression.getRows();
        this.hasGroup=SqlSegmentUtil.isNotEmpty(easyQuerySqlExpression.getGroup());
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
    public List<PropertyOrder> getOrders() {
        return this.orders;
    }

    @Override
    public List<PropertyGroup> getGroups() {
        return this.groups;
    }

    @Override
    public boolean isPaginationQuery() {
        return easyQueryPrepareParseResult.getOffset() > 0 || easyQueryPrepareParseResult.getRows() > 0;
    }

    @Override
    public boolean hasGroupQuery() {
        return hasGroup;
    }

    @Override
    public long getOffset() {
        return easyQueryPrepareParseResult.getOffset();
    }

    @Override
    public long getRows() {
        return easyQueryPrepareParseResult.getRows();
    }

    @Override
    public long getRewriteOffset() {
        return offset;
    }

    @Override
    public long getRewriteRows() {
        return rows;
    }
    @Override
    public boolean isStartsWithGroupByInOrderBy() {
        return easyQueryPrepareParseResult.isStartsWithGroupByInOrderBy();
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
