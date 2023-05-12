package com.easy.query.test.sharding;

import java.util.Comparator;
import java.util.Objects;

/**
 * create time 2023/5/11 22:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceAndTableComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        String[] sp1 = o1.split("\"");
        String ds1 = sp1[0];
        String[] sp2 = o2.split("\"");
        String ds2 = sp2[0];
        int i = ds1.compareToIgnoreCase(ds2);
        if(i!=0){
            return i;
        }
        String table1 = sp1[1];
        String table2 = sp2[1];
        return table1.compareToIgnoreCase(table2);
    }
}
