package com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation;

import java.util.Arrays;

/**
 * create time 2023/4/28 21:04
 * 文件说明
 *
 * @author xuejiaming
 */
public enum AggregationType {

    UNKNOWN,MAX, MIN, SUM, COUNT, AVG, BIT_XOR,LENGTH, COUNT_DISTINCT, SUM_DISTINCT, AVG_DISTINCT;

    /**
     * Is aggregation type.
     *
     * @param aggregationType aggregation type
     * @return is aggregation type or not
     */
    public static boolean isAggregationType(final String aggregationType) {
        return Arrays.stream(values()).anyMatch(each -> aggregationType.equalsIgnoreCase(each.name()));
    }
}
