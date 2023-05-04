package com.easy.query.core.sharding.comparer;

/**
 * create time 2023/4/27 13:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ShardingComparer {
    int compare(Comparable<?> x,Comparable<?> b,boolean asc);
}
