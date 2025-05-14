package com.easy.query.core.expression.func;

/**
 * @author xuejiaming
 * @FileName: IEasyAggregate.java
 * @Description: 文件说明
 * create time 2023/2/18 22:24
 */
public interface ColumnFunction {
    /**
     * 传入列名:name，包装后的列名
     *
     * @param column
     * @return
     */
    String getFuncColumn(String column);

    AggregationType getAggregationType();
}
