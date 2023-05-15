package com.easy.query.core.sharding.manager;

/**
 * create time 2023/5/15 11:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultSequenceCountNode implements SequenceCountNode {
    private long total;

    public DefaultSequenceCountNode(long total) {

        this.total = total;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "total=" + total +
                '}';
    }
}
