package com.easy.query.sharding;

import java.util.Comparator;

/**
 * create time 2023/5/8 10:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class IgnoreDataSourceTimeDescComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        String tableName1 = o1.split("\\.")[1];
        String tableName2 = o2.split("\\.")[1];
        return tableName1.compareToIgnoreCase(tableName2)*-1;
    }
}
