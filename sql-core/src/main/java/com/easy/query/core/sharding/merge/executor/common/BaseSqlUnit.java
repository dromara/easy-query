package com.easy.query.core.sharding.merge.executor.common;


/**
 * create time 2023/4/14 16:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class BaseSqlUnit implements SqlUnit{
    private final String sql;
    public BaseSqlUnit(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
