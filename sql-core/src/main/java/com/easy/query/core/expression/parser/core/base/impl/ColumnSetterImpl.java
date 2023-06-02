package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.segment.ColumnWithSelfSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 */
public class ColumnSetterImpl<T> implements ColumnSetter<T> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public ColumnSetterImpl(int index, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {

        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.runtimeContext = entityExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.table = entityExpressionBuilder.getTable(index).getEntityTable();
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSetter<T> set(boolean condition, String property, Object val) {
        if (condition) {
            EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
            sqlBuilderSegment.append(new ColumnValuePredicate(table.getEntityTable(), property, val, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public ColumnSetter<T> setWithColumn(boolean condition, String property1, String property2) {
        if (condition) {
            EntityTableExpressionBuilder table1 = entityExpressionBuilder.getTable(index);
            EntityTableExpressionBuilder table2 = entityExpressionBuilder.getTable(index);
            sqlBuilderSegment.append(new ColumnWithColumnPredicate(table1.getEntityTable(), property1, table2.getEntityTable(), property2, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public ColumnSetter<T> setIncrementNumber(boolean condition, String property, Number val) {

        if (condition) {
            setSelf(true, property, val);
        }
        return this;
    }

    private void setSelf(boolean increment, String property, Number val) {

        EntityTableExpressionBuilder table = entityExpressionBuilder.getTable(index);
        ColumnWithSelfSegment columnWithSelfSegment = sqlSegmentFactory.createColumnWithSelfSegment(increment, table.getEntityTable(), property, val, SQLPredicateCompareEnum.EQ, entityExpressionBuilder.getRuntimeContext());
        sqlBuilderSegment.append(columnWithSelfSegment);
    }

    @Override
    public ColumnSetter<T> setDecrementNumber(boolean condition, String property, Number val) {

        if (condition) {
            setSelf(false, property, val);
        }
        return this;
    }
}
