package com.easy.query.core.datasource.replica.connectors;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.datasource.replica.ReplicaNode;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * create time 2023/5/12 14:46
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractAliasReplicaConnector extends AbstractReplicaConnector{
    public AbstractAliasReplicaConnector(String dataSourceName, List<ReplicaNode> replicaNodes) {
        super(dataSourceName, replicaNodes);
    }

    @Override
    public DataSourceUnit getDataSourceOrNull(String alias) {
        if(length==0){
            return null;
        }
        if (alias == null) {
            return getDataSourceByAlias(alias);
        } else {
            ReplicaNode replicaNode = EasyCollectionUtil.firstOrDefault(replicaNodes, o -> Objects.equals(o.getAlias(), alias), null);
            if (replicaNode == null) {
                return null;
            }
            return replicaNode.getDataSourceUnit();
        }
    }
    protected abstract DataSourceUnit getDataSourceByAlias(String alias);
}
