package com.easy.query.core.api.dynamic.sort.internal;

/**
 * create time 2023/7/22 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectSortEntry {
    private final boolean asc;
    private final int tableIndex;

    public ObjectSortEntry(boolean asc, int tableIndex){

        this.asc = asc;
        this.tableIndex = tableIndex;
    }

    public boolean isAsc() {
        return asc;
    }

    public int getTableIndex() {
        return tableIndex;
    }
}
