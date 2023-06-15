package com.easy.query.core.datasource.replica;

import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.enums.conn.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.conn.DataSourceWrapper;
import com.easy.query.core.basic.jdbc.conn.impl.DefaultDataSourceWrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.EasyQueryReplicaOption;
import com.easy.query.core.datasource.DefaultDataSourceManager;
import com.easy.query.core.datasource.replica.connectors.LoopReplicaConnector;
import com.easy.query.core.datasource.replica.connectors.RandomReplicaConnector;
import com.easy.query.core.datasource.replica.connectors.ReplicaConnector;
import com.easy.query.core.enums.replica.ReplicaUseStrategyEnum;
import com.easy.query.core.util.EasyMapUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/5/12 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultReplicaDataSourceManager extends DefaultDataSourceManager implements ReplicaDataSourceManager {

    private final Map<String, ReplicaConnector> replicaDataSource = new ConcurrentHashMap<>();
    private final EasyQueryOption easyQueryOption;

    public DefaultReplicaDataSourceManager(EasyQueryOption easyQueryOption, DataSource defaultDataSource, DataSourceUnitFactory dataSourceUnitFactory) {
        super(easyQueryOption, defaultDataSource,dataSourceUnitFactory);
        this.easyQueryOption = easyQueryOption;
        EasyQueryReplicaOption replicaOption = easyQueryOption.getReplicaOption();
        if (replicaOption == null) {
            throw new IllegalArgumentException("replica data source manager cant use empty replica");
        }
        initReplicaConnector(replicaOption);
    }

    private void initReplicaConnector(EasyQueryReplicaOption replicaOption) {

        Map<String, Map<String, ShardingDataSource>> replicaConfig = replicaOption.getReplicaConfig();
        for (Map.Entry<String, Map<String, ShardingDataSource>> dataSourceMap : replicaConfig.entrySet()) {
            String dataSource = dataSourceMap.getKey();
            Set<Map.Entry<String, ShardingDataSource>> entries = dataSourceMap.getValue().entrySet();
            ArrayList<ReplicaNode> replicaNodes = new ArrayList<>(entries.size());
            for (Map.Entry<String, ShardingDataSource> replicaKv : entries) {
                ShardingDataSource shardingDataSource = replicaKv.getValue();
                replicaNodes.add(new ReplicaNode(replicaKv.getKey(), dataSourceUnitFactory.createDataSourceUnit(shardingDataSource.getDataSourceName(),shardingDataSource.getDataSource(),shardingDataSource.getDataSourceMergePoolSize())));
            }
            ReplicaConnector replicaConnector = Objects.equals(ReplicaUseStrategyEnum.Loop, replicaOption.getReplicaUseStrategy()) ? new LoopReplicaConnector(dataSource, replicaNodes) : new RandomReplicaConnector(dataSource, replicaNodes);
            replicaDataSource.put(dataSource, replicaConnector);
        }
    }

    private ReplicaConnector createEmptyReplicaConnector(String dataSourceName) {
        return Objects.equals(ReplicaUseStrategyEnum.Loop, easyQueryOption.getReplicaOption().getReplicaUseStrategy()) ?
                new LoopReplicaConnector(dataSourceName, new ArrayList<>())
                : new RandomReplicaConnector(dataSourceName, new ArrayList<>());
    }

    @Override
    public boolean addDataSource(String dataSourceName, String replicaAlias, DataSource dataSource,int dataSourcePoolSize) {
        ReplicaConnector replicaConnector = EasyMapUtil.computeIfAbsent(replicaDataSource,dataSourceName, k -> createEmptyReplicaConnector(dataSourceName));
        DataSourceUnit dataSourceUnit = dataSourceUnitFactory.createDataSourceUnit(dataSourceName, dataSource, dataSourcePoolSize);
        return replicaConnector.addReplicaNode(new ReplicaNode(replicaAlias,dataSourceUnit));
    }

    @Override
    public DataSourceUnit getDataSourceOrNull(String dataSourceName, String replicaAlias) {
        ReplicaConnector replicaConnector = replicaDataSource.get(dataSourceName);
        if(replicaConnector!=null){
            return replicaConnector.getDataSourceOrNull(replicaAlias);
        }
        return null;
    }

    @Override
    public DataSourceWrapper getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {
        if(Objects.equals(ConnectionStrategyEnum.IndependentConnectionReplica,connectionStrategy)){

            ReplicaConnector replicaConnector = replicaDataSource.get(dataSourceName);
            if(replicaConnector!=null){
                DataSourceUnit dataSourceUnit = replicaConnector.getDataSourceOrNull(null);
                if(dataSourceUnit!=null){
                    return new DefaultDataSourceWrapper(dataSourceUnit,connectionStrategy);
                }
            }
            return super.getDataSourceOrNull(dataSourceName,ConnectionStrategyEnum.IndependentConnectionMaster);
        }
        return super.getDataSourceOrNull(dataSourceName,connectionStrategy);
    }
}
