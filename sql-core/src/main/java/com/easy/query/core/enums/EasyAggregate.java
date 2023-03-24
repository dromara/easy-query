package com.easy.query.core.enums;

/**
 * @FileName: EasyAggregate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:24
 * @author xuejiaming
 */
public enum EasyAggregate implements IEasyFunc {
    SUM("SUM(%s)"),
    COUNT("COUNT(%s)"),
    COUNT_DISTINCT("COUNT(DISTINCT %s)"),
    MAX("MAX(%s)"),
    MIN("MIN(%s)"),
    AVG("AVG(%s)"),
    LEN("LEN(%s)");
    private final String aggregate;

    EasyAggregate(String aggregate){

        this.aggregate = aggregate;
    }

    @Override
    public String getFuncColumn(String column) {
        return String.format(aggregate,column);
    }
}
