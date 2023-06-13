package com.easy.query.core.sharding.common;

import com.easy.query.core.sharding.router.table.TableUnit;

import java.util.Comparator;

/**
 * create time 2023/5/16 09:24
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DataSourceThenTableNameStringComparator implements Comparator<TableUnit> {
    private final boolean reverse;

    public DataSourceThenTableNameStringComparator(){
        this(false);
    }
    public DataSourceThenTableNameStringComparator(boolean reverse){

        this.reverse = reverse;
    }
    @Override
    public int compare(TableUnit o1, TableUnit o2) {

        int i = compare0(o1.getDataSourceName(),o2.getDataSourceName());
        if(i!=0){
            return resultCompare(i);
        }
        i = compare0(o1.getActualTableName(),o2.getActualTableName());
        return resultCompare(i);
    }
    private int compare0(String o1,String o2){
        return o1.compareToIgnoreCase(o2);
    }
    private int resultCompare(int i){
        if(reverse){
            return -1*i;
        }
        return i;
    }
}
