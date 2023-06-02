package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

import java.util.Collection;
import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/8 12:26
 */
public class OrderColumnSelectorImpl<T1> implements ColumnSelector<T1> {
    protected final int index;
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLSegmentFactory sqlSegmentFactory;
    protected boolean asc;

    public OrderColumnSelectorImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        this.index = index;
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.sqlSegmentFactory = entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
        this.table = entityQueryExpressionBuilder.getTable(index).getEntityTable();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSelector<T1> column(String property) {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        OrderByColumnSegment orderByColumnSegment = sqlSegmentFactory.createOrderByColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), asc);
        entityQueryExpressionBuilder.getOrder().append(orderByColumnSegment);
        return this;
    }

    @Override
    public ColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        OrderFuncColumnSegment orderFuncColumnSegment = sqlSegmentFactory.createOrderFuncColumnSegment(table.getEntityTable(), propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, asc);
        entityQueryExpressionBuilder.getOrder().append(orderFuncColumnSegment);
        return this;
    }

    @Override
    public ColumnSelector<T1> columnIgnore(String property) {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        entityQueryExpressionBuilder.getOrder().getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table.getEntityTable()) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return this;
    }

    @Override
    public ColumnSelector<T1> columnAll() {
        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            OrderByColumnSegment orderByColumnSegment = sqlSegmentFactory.createOrderByColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), asc);
            entityQueryExpressionBuilder.getOrder().append(orderByColumnSegment);
        }
        return this;
    }


    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}
