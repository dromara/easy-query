package com.easy.query.core.sharding.initializer;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * create time 2023/5/7 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInitializerBuilder<T> {
    private final EntityMetadata entityMetadata;
    private Map<String, Collection<String>> actualTableNames;
    private ShardingOrderBuilder<T> shardingOrderBuilder;

    public ShardingInitializerBuilder(EntityMetadata entityMetadata) {

        this.entityMetadata = entityMetadata;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public ShardingInitOption build() {
        Comparator<String> defaultTableNameComparator = null;
        Map<String, Boolean> sequenceProperties = null;
        boolean reverse = false;
        ExecuteMethodBehavior executeMethodBehavior=null;
        int connectionsLimit = 0;
        if (shardingOrderBuilder != null) {
            defaultTableNameComparator = shardingOrderBuilder.getDefaultTableNameComparator();
            sequenceProperties = shardingOrderBuilder.getSequenceProperties();
            reverse = shardingOrderBuilder.isDefaultReverse();
            connectionsLimit = shardingOrderBuilder.getConnectionsLimit();
            executeMethodBehavior=shardingOrderBuilder.getExecuteMethodBehavior();
            executeMethodBehavior.removeBehavior(ExecuteMethodEnum.UNKNOWN);
        }
        return new ShardingInitOption(actualTableNames,defaultTableNameComparator,sequenceProperties,reverse,connectionsLimit,executeMethodBehavior);
    }

    public ShardingInitializerBuilder<T> actualTableNameInit(Map<String, Collection<String>> actualTableNames) {
        this.actualTableNames = actualTableNames;
        return this;
    }
    /**
     * 实际表排序
     * @param defaultTableNameComparator
     * @param defaultReverse 默认使用是否取反
     * @return
     */
    public ShardingOrderBuilder<T> orderConfigure(Comparator<String> defaultTableNameComparator, boolean defaultReverse) {

        this.shardingOrderBuilder = new ShardingOrderBuilder<>(entityMetadata, defaultTableNameComparator,  defaultReverse);
        return shardingOrderBuilder;
    }
}
