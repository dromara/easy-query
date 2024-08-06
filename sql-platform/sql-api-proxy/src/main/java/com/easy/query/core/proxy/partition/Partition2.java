package com.easy.query.core.proxy.partition;


import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartitionResult;

/**
 * create time 2024/8/4 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Partition2<TEntity, TValue1, TValue2> implements PartitionResult<TEntity> {
    public static final String PARTITION_COLUMN1 = PartitionResult.PARTITION_PREFIX + "column1";
    public static final String PARTITION_COLUMN2 = PartitionResult.PARTITION_PREFIX + "column2";
    private TEntity entity;
    private TValue1 partitionColumn1;
    private TValue2 partitionColumn2;

    public TEntity getEntity() {
        return entity;
    }

    public void setEntity(TEntity entity) {
        this.entity = entity;
    }

    public TValue1 getPartitionColumn1() {
        return partitionColumn1;
    }

    public void setPartitionColumn1(TValue1 column1) {
        this.partitionColumn1 = column1;
    }
    public TValue2 getPartitionColumn2() {
        return partitionColumn2;
    }

    public void setPartitionColumn2(TValue2 column2) {
        this.partitionColumn2 = column2;
    }
}
