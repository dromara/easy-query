package com.easy.query.test.sharding;

import com.easy.query.core.sharding.route.table.TableUnit;

import java.util.Comparator;

/**
 * create time 2023/5/11 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceAndTableComparator implements Comparator<TableUnit> {
    @Override
    public int compare(TableUnit o1, TableUnit o2) {
        int i = o1.getDataSourceName().compareToIgnoreCase(o2.getDataSourceName());
        if(i!=0){
            return i;
        }
        return o1.getActualTableName().compareToIgnoreCase(o2.getActualTableName());
    }
}
