package com.easy.query.core.sharding.initializer;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * create time 2023/5/7 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingEntityBuilder<T> {
    private final EntityMetadata entityMetadata;
    private Map<String, Collection<String>> actualTableNames;
    private ShardingSequenceBuilder<T> shardingOrderBuilder;

    public ShardingEntityBuilder(EntityMetadata entityMetadata) {

        this.entityMetadata = entityMetadata;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public ShardingInitOption build() {
        Comparator<String> defaultTableNameComparator = null;
        Map<String, Boolean> sequenceProperties = null;
        ExecuteMethodBehavior executeMethodBehavior=null;
        ConnectionModeEnum connectionMode=null;
        int connectionsLimit = 0;
        if (shardingOrderBuilder != null) {
            defaultTableNameComparator = shardingOrderBuilder.getDefaultTableNameComparator();
            sequenceProperties = shardingOrderBuilder.getSequenceProperties();
            connectionMode=shardingOrderBuilder.getConnectionMode();
            connectionsLimit = shardingOrderBuilder.getMaxShardingQueryLimit();
            executeMethodBehavior=shardingOrderBuilder.getExecuteMethodBehavior();
            executeMethodBehavior.removeMethod(ExecuteMethodEnum.UNKNOWN);
        }
        return new ShardingInitOption(actualTableNames,defaultTableNameComparator,sequenceProperties,connectionMode,connectionsLimit,executeMethodBehavior);
    }

    public ShardingEntityBuilder<T> actualTableNameInit(Map<String, Collection<String>> actualTableNames) {
        this.actualTableNames = actualTableNames;
        return this;
    }
    /**
     * 实际表asc下的排序
     * @param defaultTableNameComparator 默认没有匹配orderby的时候也会将表进行当前排序器进行排序后再分批处理
     * @return
     */
    public ShardingSequenceBuilder<T> ascSequenceConfigure(Comparator<String> defaultTableNameComparator) {

        this.shardingOrderBuilder = new ShardingSequenceBuilder<>(entityMetadata, defaultTableNameComparator);
        return shardingOrderBuilder;
    }
}
