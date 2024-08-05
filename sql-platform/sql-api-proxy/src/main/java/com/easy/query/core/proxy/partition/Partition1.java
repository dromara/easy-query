package com.easy.query.core.proxy.partition;


import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartitionResult;

/**
 * create time 2024/8/4 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Partition1<TEntity, TValue> implements PartitionResult<TEntity> {
    public static final String PARTITION_COLUMN1 = PartitionResult.PARTITION_PREFIX + "column1";
    private TEntity entity;
    private TValue partitionColumn1;

    public TEntity getEntity() {
        return entity;
    }

    public void setEntity(TEntity entity) {
        this.entity = entity;
    }

    public TValue getPartitionColumn1() {
        return partitionColumn1;
    }

    public void setPartitionColumn1(TValue column1) {
        this.partitionColumn1 = column1;
    }
}
