package com.easy.query.core.expression.segment;

/**
 * create time 2023/4/30 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderByColumnSegment extends ColumnSegment{
    GroupByColumnSegment createGroupByColumnSegment();
    boolean isAsc();
}
