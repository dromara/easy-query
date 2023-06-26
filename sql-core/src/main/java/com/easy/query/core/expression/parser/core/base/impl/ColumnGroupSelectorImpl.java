package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/12 21:36
 */
public class ColumnGroupSelectorImpl<T1> implements ColumnGroupSelector<T1> {
    private final TableAvailable table;
    private final GroupSelector groupSelector;

    public ColumnGroupSelectorImpl(TableAvailable table, GroupSelector groupSelector) {
        this.groupSelector = groupSelector;
        this.table = table;
    }


    @Override
    public GroupSelector getGroupSelector() {
        return groupSelector;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnGroupSelector<T1> column(String property) {
        groupSelector.column(table,property);
        return this;
    }

    @Override
    public ColumnGroupSelector<T1> columnConst(String columnConst) {
        groupSelector.columnConst(columnConst);
        return this;
    }

    @Override
    public ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        groupSelector.columnFunc(table,columnPropertyFunction);
        return this;
    }
}
