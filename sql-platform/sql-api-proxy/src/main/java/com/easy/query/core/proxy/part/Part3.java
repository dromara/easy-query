package com.easy.query.core.proxy.part;


import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartResult;

/**
 * create time 2024/8/4 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part3<TEntity, TValue1, TValue2, TValue3> implements PartResult<TEntity> {
    public static final String PART_COLUMN1 = PartResult.PART_PREFIX + "column1";
    public static final String PART_COLUMN2 = PartResult.PART_PREFIX + "column2";
    public static final String PART_COLUMN3 = PartResult.PART_PREFIX + "column3";
    private TEntity entity;
    private TValue1 partColumn1;
    private TValue2 partColumn2;
    private TValue3 partColumn3;

    public TEntity getEntity() {
        return entity;
    }

    public void setEntity(TEntity entity) {
        this.entity = entity;
    }

    public TValue1 getPartColumn1() {
        return partColumn1;
    }

    public void setPartColumn1(TValue1 column1) {
        this.partColumn1 = column1;
    }
    public TValue2 getPartColumn2() {
        return partColumn2;
    }

    public void setPartColumn2(TValue2 column2) {
        this.partColumn2 = column2;
    }

    public TValue3 getPartColumn3() {
        return partColumn3;
    }

    public void setPartColumn3(TValue3 partColumn3) {
        this.partColumn3 = partColumn3;
    }
}
