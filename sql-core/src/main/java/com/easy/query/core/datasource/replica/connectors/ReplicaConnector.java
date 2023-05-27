package com.easy.query.core.datasource.replica.connectors;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.datasource.replica.ReplicaNode;

import javax.sql.DataSource;

/**
 * create time 2023/5/12 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ReplicaConnector {
    String getDataSourceName();
    DataSourceUnit getDataSourceOrNull(String alias);
    boolean addReplicaNode(ReplicaNode replicaNode);
}
