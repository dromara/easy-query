package com.easy.query.core.datasource.replica;

import com.easy.query.core.datasource.DataSourceUnit;

/**
 * create time 2023/5/12 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReplicaNode {
    private final String alias;
    private final DataSourceUnit dataSourceUnit;

    public ReplicaNode(String alias, DataSourceUnit dataSourceUnit){
        this.alias = alias;

        this.dataSourceUnit = dataSourceUnit;
    }

    public String getAlias() {
        return alias;
    }

    public DataSourceUnit getDataSourceUnit() {
        return dataSourceUnit;
    }
}
