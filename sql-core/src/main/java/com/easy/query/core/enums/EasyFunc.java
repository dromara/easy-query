package com.easy.query.core.enums;

import com.easy.query.core.basic.jdbc.executor.internal.merge.result.aggregation.AggregationType;

/**
 * @FileName: IEasyAggregate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:24
 * @author xuejiaming
 */
public interface EasyFunc {
    /**
     * 传入列名:name，返回substring(name,1,1)
     * @param column
     * @return
     */
    String getFuncColumn(String column);
    AggregationType getAggregationType();
}
