package com.easy.query.core.api.dynamic.sort.internal;

import com.easy.query.core.func.def.enums.OrderByModeEnum;

/**
 * create time 2023/7/22 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectSortEntry {
    private final boolean asc;
    private final int tableIndex;
    private final OrderByModeEnum orderByMode;

    public ObjectSortEntry(boolean asc, int tableIndex,OrderByModeEnum orderByMode){

        this.asc = asc;
        this.tableIndex = tableIndex;
        this.orderByMode = orderByMode;
    }

    public boolean isAsc() {
        return asc;
    }

    public int getTableIndex() {
        return tableIndex;
    }

    public OrderByModeEnum getOrderByMode() {
        return orderByMode;
    }
}
