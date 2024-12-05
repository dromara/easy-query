package com.easy.query.core.expression.segment;

/**
 * create time 2024/12/3 13:45
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderBySQLNativeSegment extends SQLNativeSegment, OrderBySegment{
    @Override
    OrderBySQLNativeSegment cloneSQLColumnSegment();
}
