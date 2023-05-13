package com.easy.query.core.sharding.manager;

/**
 * create time 2023/5/12 20:51
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryCountResult {
    private final long total;

    public QueryCountResult(long total){

        this.total = total;
    }



    public long getTotal() {
        return total;
    }
}
