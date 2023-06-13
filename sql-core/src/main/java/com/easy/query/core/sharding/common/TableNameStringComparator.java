package com.easy.query.core.sharding.common;

import com.easy.query.core.sharding.router.table.TableUnit;

import java.util.Comparator;

/**
 * create time 2023/5/16 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableNameStringComparator implements Comparator<TableUnit> {
    private final boolean reverse;

    public TableNameStringComparator(){
        this(false);
    }
    public TableNameStringComparator(boolean reverse){

        this.reverse = reverse;
    }
    @Override
    public int compare(TableUnit o1, TableUnit o2) {
        int i = o1.getActualTableName().compareToIgnoreCase(o2.getActualTableName());
        if(reverse){
            return -1*i;
        }
        return i;
    }
}
