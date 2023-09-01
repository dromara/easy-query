package com.easy.query.core.expression.segment.index;

/**
 * create time 2023/9/1 22:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SegmentIndex {
    boolean contains(Class<?> entityClass, String propertyName);
}
