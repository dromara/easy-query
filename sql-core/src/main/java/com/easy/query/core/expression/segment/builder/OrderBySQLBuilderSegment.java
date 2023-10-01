package com.easy.query.core.expression.segment.builder;

/**
 * create time 2023/10/1 10:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderBySQLBuilderSegment extends SQLBuilderSegment{
    /**
     * 是否可以反向排序
     * @return
     */
    boolean reverseOrder();
}
