package com.easy.query.core.configuration;

import com.easy.query.core.enums.replica.ReplicaBehaviorEnum;
import com.easy.query.core.enums.replica.ReplicaUseStrategyEnum;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/5/12 13:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryReplicaOption {
    private final ReplicaBehaviorEnum replicaBehavior;
    private final ReplicaUseStrategyEnum replicaUseStrategy;
    private final Map<String, Map<String, ShardingDataSource>> replicaConfig;

    public EasyQueryReplicaOption(ReplicaBehaviorEnum replicaBehavior, ReplicaUseStrategyEnum replicaUseStrategy, Map<String,Map<String, ShardingDataSource>> replicaConfig){

        this.replicaBehavior = replicaBehavior;
        this.replicaUseStrategy = replicaUseStrategy;
        this.replicaConfig = replicaConfig;
    }

    public ReplicaBehaviorEnum getReplicaBehavior() {
        return replicaBehavior;
    }

    public ReplicaUseStrategyEnum getReplicaUseStrategy() {
        return replicaUseStrategy;
    }

    public Map<String, Map<String, ShardingDataSource>> getReplicaConfig() {
        return replicaConfig;
    }
}
