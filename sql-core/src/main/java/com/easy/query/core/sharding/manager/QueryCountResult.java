package com.easy.query.core.sharding.manager;

/**
 * create time 2023/5/12 20:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryCountResult {
    private final int index;
    private final long total;
    private final String sql;

    public QueryCountResult(int index, long total,String sql){

        this.index = index;
        this.total = total;
        this.sql = sql;
    }


    public int getIndex() {
        return index;
    }

    public long getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "{" +
                "index=" + index +
                ", total=" + total +
                ", sql=" + sql +
                '}';
    }
}
