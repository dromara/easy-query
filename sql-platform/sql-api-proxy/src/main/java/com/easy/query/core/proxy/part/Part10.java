package com.easy.query.core.proxy.part;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartResult;

/**
 * create time 2025/4/15 08:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class Part10<TEntity, TValue1, TValue2, TValue3, TValue4, TValue5,
        TValue6, TValue7, TValue8, TValue9, TValue10>
        implements PartResult<TEntity> {
    public static final String PART_COLUMN1 = PartResult.PART_PREFIX + "column1";
    public static final String PART_COLUMN2 = PartResult.PART_PREFIX + "column2";
    public static final String PART_COLUMN3 = PartResult.PART_PREFIX + "column3";
    public static final String PART_COLUMN4 = PartResult.PART_PREFIX + "column4";
    public static final String PART_COLUMN5 = PartResult.PART_PREFIX + "column5";
    public static final String PART_COLUMN6 = PartResult.PART_PREFIX + "column6";
    public static final String PART_COLUMN7 = PartResult.PART_PREFIX + "column7";
    public static final String PART_COLUMN8 = PartResult.PART_PREFIX + "column8";
    public static final String PART_COLUMN9 = PartResult.PART_PREFIX + "column9";
    public static final String PART_COLUMN10 = PartResult.PART_PREFIX + "column10";
    private TEntity entity;
    private TValue1 partColumn1;
    private TValue2 partColumn2;
    private TValue3 partColumn3;
    private TValue4 partColumn4;
    private TValue5 partColumn5;
    private TValue6 partColumn6;
    private TValue7 partColumn7;
    private TValue8 partColumn8;
    private TValue9 partColumn9;
    private TValue10 partColumn10;

    @Override
    public TEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(TEntity entity) {
        this.entity = entity;
    }

    public TValue1 getPartColumn1() {
        return partColumn1;
    }

    public void setPartColumn1(TValue1 partColumn1) {
        this.partColumn1 = partColumn1;
    }

    public TValue2 getPartColumn2() {
        return partColumn2;
    }

    public void setPartColumn2(TValue2 partColumn2) {
        this.partColumn2 = partColumn2;
    }

    public TValue3 getPartColumn3() {
        return partColumn3;
    }

    public void setPartColumn3(TValue3 partColumn3) {
        this.partColumn3 = partColumn3;
    }

    public TValue4 getPartColumn4() {
        return partColumn4;
    }

    public void setPartColumn4(TValue4 partColumn4) {
        this.partColumn4 = partColumn4;
    }

    public TValue5 getPartColumn5() {
        return partColumn5;
    }

    public void setPartColumn5(TValue5 partColumn5) {
        this.partColumn5 = partColumn5;
    }

    public TValue6 getPartColumn6() {
        return partColumn6;
    }

    public void setPartColumn6(TValue6 partColumn6) {
        this.partColumn6 = partColumn6;
    }

    public TValue7 getPartColumn7() {
        return partColumn7;
    }

    public void setPartColumn7(TValue7 partColumn7) {
        this.partColumn7 = partColumn7;
    }

    public TValue8 getPartColumn8() {
        return partColumn8;
    }

    public void setPartColumn8(TValue8 partColumn8) {
        this.partColumn8 = partColumn8;
    }

    public TValue9 getPartColumn9() {
        return partColumn9;
    }

    public void setPartColumn9(TValue9 partColumn9) {
        this.partColumn9 = partColumn9;
    }

    public TValue10 getPartColumn10() {
        return partColumn10;
    }

    public void setPartColumn10(TValue10 partColumn10) {
        this.partColumn10 = partColumn10;
    }
}