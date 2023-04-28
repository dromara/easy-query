package com.easy.query.core.sharding.merge;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.sharding.merge.segment.EntityPropertyOrder;
import com.easy.query.core.sharding.merge.segment.PropertyOrder;
import com.easy.query.core.util.SqlSegmentUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/27 15:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingQueryEasyStreamMergeContext extends EntityStreamMergeContext {
    private final EasyQueryPrepareParseResult easyQueryPrepareParseResult;
    protected final List<PropertyOrder> orders;
    protected final EasyQuerySqlExpression easyQuerySqlExpression;
    protected final boolean hasGroup;
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
    }

    private List<PropertyOrder> getOrders(EasyQuerySqlExpression easyQuerySqlExpression) {
        if (isShardingMerge() && SqlSegmentUtil.isNotEmpty(easyQuerySqlExpression.getOrder())) {
            List<PropertyOrder> orders = new ArrayList<>();
            SqlBuilderSegment projects = easyQuerySqlExpression.getProjects();
            for (SqlSegment sqlSegment : easyQuerySqlExpression.getOrder().getSqlSegments()) {
                if (sqlSegment instanceof OrderColumnSegment) {
                    OrderColumnSegment orderColumnSegment = (OrderColumnSegment) sqlSegment;

                    PropertyOrder propertyOrder = findFirstPropertyNotNull(projects.getSqlSegments(), orderColumnSegment, easyQuerySqlExpression);
                    orders.add(propertyOrder);
                }
            }
            return orders;
        } else {
            return EMPTY_ORDERS;
        }
    }

    private PropertyOrder findFirstPropertyNotNull(List<SqlSegment> selectColumns, OrderColumnSegment orderColumnSegment, EasyQuerySqlExpression easyQuerySqlExpression) {
        int tableIndex = orderColumnSegment.getTable().getIndex();
        String propertyName = orderColumnSegment.getPropertyName();
        boolean asc = orderColumnSegment.isAsc();
        int selectIndex = -1;
        for (SqlSegment selectColumn : selectColumns) {
            selectIndex++;
            if (selectColumn instanceof ColumnSegment) {
                ColumnSegment selectColumnSegment = (ColumnSegment) selectColumn;
                String selectPropertyName = selectColumnSegment.getPropertyName();
                if (selectColumnSegment.getTable().getIndex() == tableIndex && Objects.equals(selectPropertyName, propertyName)) {
                    EasyTableSqlExpression table = easyQuerySqlExpression.getTable(tableIndex);
                    return new EntityPropertyOrder(table, propertyName, selectIndex, asc);
                }
            }
        }
        throw new EasyQueryInvalidOperationException("sharding query order: [" + propertyName + "] not found in select projects");
    }

    @Override
    public List<PropertyOrder> getOrders() {
        return this.orders;
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
    public boolean groupQueryMemoryMerge() {
        return false;
    }

}
