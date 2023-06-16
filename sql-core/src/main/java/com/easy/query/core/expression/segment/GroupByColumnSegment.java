package com.easy.query.core.expression.segment;

/**
 * create time 2023/4/30 09:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupByColumnSegment extends ColumnSegment{
    OrderBySegment createOrderByColumnSegment(boolean asc);
}
