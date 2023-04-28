package com.easy.query.test;

import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.sharding.merge.result.aggregation.AggregationType;

/**
 * create time 2023/3/30 22:13
 * 文件说明
 *
 * @author xuejiaming
 */
public enum IfNullEasyFunc implements EasyFunc {
    IfNull("IfNull(%s,'')",AggregationType.UNKNOWN);
    private final String format;
    private final AggregationType aggregationType;

    IfNullEasyFunc(String format,AggregationType aggregationType){

        this.format = format;
        this.aggregationType = aggregationType;
    }
    @Override
    public String getFuncColumn(String column) {
        return String.format(format,column);
    }

    @Override
    public AggregationType getAggregationType() {
        return aggregationType;
    }
}
