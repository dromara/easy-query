package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

import java.util.Collection;
import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 21:31
 */
public class ColumnSetSelectorImpl<T> implements ColumnSelector<T> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlSegmentBuilder;

    public ColumnSetSelectorImpl(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder) {

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.table = entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlSegmentBuilder = sqlSegmentBuilder;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSelector<T> column(String property) {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table.getEntityTable(), property, entityExpressionBuilder.getRuntimeContext()));
        return this;
    }

    @Override
    public ColumnSelector<T> columnIgnore(String property) {

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table.getEntityTable()) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return this;
    }

    @Override
    public ColumnSelector<T> columnAll() {
        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table.getEntityTable(), property, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public ColumnSelector<T> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        throw new UnsupportedOperationException();
    }
}
