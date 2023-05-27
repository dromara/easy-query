package com.easy.query.core.datasource.replica.connectors;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.datasource.replica.ReplicaNode;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * create time 2023/5/12 14:23
 * 文件说明
 *
 * @author xuejiaming
 */
public final class LoopReplicaConnector extends AbstractAliasReplicaConnector {
    private final AtomicLong seed = new AtomicLong(0);

    public LoopReplicaConnector(String dataSourceName, List<ReplicaNode> replicaNodes) {
        super(dataSourceName, replicaNodes);
    }

    @Override
    protected DataSourceUnit getDataSourceByAlias(String alias) {
        if (length == 1) {
            return replicaNodes.get(0).getDataSourceUnit();
        }
        long nextSeed = seed.incrementAndGet();
        int next = (int) (nextSeed % length);
        if (next < 0)
            return replicaNodes.get(Math.abs(next)).getDataSourceUnit();
        return replicaNodes.get(next).getDataSourceUnit();
    }

}
