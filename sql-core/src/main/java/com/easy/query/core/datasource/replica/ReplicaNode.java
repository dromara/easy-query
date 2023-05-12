package com.easy.query.core.datasource.replica;

import javax.sql.DataSource;

/**
 * create time 2023/5/12 14:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReplicaNode {
    private final String alias;
    private final DataSource dataSource;

    public ReplicaNode(String alias, DataSource dataSource){
        this.alias = alias;

        this.dataSource = dataSource;
    }

    public String getAlias() {
        return alias;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
