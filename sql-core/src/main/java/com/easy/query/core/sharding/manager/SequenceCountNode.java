package com.easy.query.core.sharding.manager;

/**
 * create time 2023/5/15 10:30
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SequenceCountNode {
    long getTotal();
    void setTotal(long total);
}
