package com.easy.query.core.sharding.common;

import java.util.Comparator;

/**
 * create time 2023/6/13 18:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class IgnoreCaseStringComparator implements Comparator<String> {
    public static Comparator<String> DEFAULT=new IgnoreCaseStringComparator();
    @Override
    public int compare(String s1, String s2) {
        return String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
    }
}
