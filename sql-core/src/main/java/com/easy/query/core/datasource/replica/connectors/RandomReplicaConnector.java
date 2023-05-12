package com.easy.query.core.datasource.replica.connectors;

import com.easy.query.core.datasource.replica.ReplicaNode;

import javax.sql.DataSource;
import java.util.List;
import java.util.Random;

/**
 * create time 2023/5/12 14:45
 * 文件说明
 *
 * @author xuejiaming
 */
public final class RandomReplicaConnector extends AbstractAliasReplicaConnector {
    public RandomReplicaConnector(String dataSourceName, List<ReplicaNode> replicaNodes) {
        super(dataSourceName, replicaNodes);
    }
    @Override
    protected DataSource getDataSourceByAlias(String alias) {
        if(length==1){
            return replicaNodes.get(0).getDataSource();
        }
        int next = new Random().nextInt(length);
        return replicaNodes.get(next).getDataSource();
    }
}
