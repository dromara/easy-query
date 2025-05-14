package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/12 21:36
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
    public ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        groupSelector.columnFunc(table,columnPropertyFunction);
        return this;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(groupSelector);
    }

    @Override
    public ColumnGroupSelector<T1> castChain() {
        return this;
    }
}
