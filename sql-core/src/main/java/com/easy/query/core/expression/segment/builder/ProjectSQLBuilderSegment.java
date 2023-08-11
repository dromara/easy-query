package com.easy.query.core.expression.segment.builder;

/**
 * create time 2023/5/9 13:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProjectSQLBuilderSegment extends SQLBuilderSegment {
    boolean hasAggregateColumns();
}
