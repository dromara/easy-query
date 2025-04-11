package com.easy.query.core.proxy.partition;


import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartResult;

/**
 * create time 2024/8/4 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part1<TEntity, TValue> implements PartResult<TEntity> {
    public static final String PART_COLUMN1 = PartResult.PART_PREFIX + "column1";
    private TEntity entity;
    private TValue partColumn1;

    public TEntity getEntity() {
        return entity;
    }

    public void setEntity(TEntity entity) {
        this.entity = entity;
    }

    public TValue getPartColumn1() {
        return partColumn1;
    }

    public void setPartColumn1(TValue column1) {
        this.partColumn1 = column1;
    }
}
