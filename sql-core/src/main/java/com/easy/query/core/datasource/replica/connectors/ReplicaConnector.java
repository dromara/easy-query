package com.easy.query.core.datasource.replica.connectors;

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
    DataSource getDataSourceOrNull(String alias);
    boolean addReplicaNode(ReplicaNode replicaNode);
}
