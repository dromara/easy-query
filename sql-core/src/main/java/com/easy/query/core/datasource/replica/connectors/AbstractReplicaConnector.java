package com.easy.query.core.datasource.replica.connectors;

import com.easy.query.core.datasource.replica.ReplicaNode;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/5/12 14:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractReplicaConnector implements ReplicaConnector{
    protected final String dataSourceName;
    protected final List<ReplicaNode> replicaNodes;
    protected int length;

    public AbstractReplicaConnector(String dataSourceName, List<ReplicaNode> replicaNodes){
        this.replicaNodes = replicaNodes;
        this.length=replicaNodes.size();
        this.dataSourceName = dataSourceName;

    }
    @Override
    public String getDataSourceName() {
        return dataSourceName;
    }
//
//    @Override
//    public DataSource getDataSourceNotNull(String alias) {
//        return null;
//    }

    @Override
    public synchronized boolean addReplicaNode(ReplicaNode replicaNode) {
        boolean any = EasyCollectionUtil.any(replicaNodes, o -> Objects.equals(o.getDataSource(), replicaNode.getDataSource()));
        if(any){
            return false;
        }
        replicaNodes.add(replicaNode);
        return true;
    }
}
