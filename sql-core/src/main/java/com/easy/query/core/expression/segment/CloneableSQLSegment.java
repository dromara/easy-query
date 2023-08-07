package com.easy.query.core.expression.segment;

/**
 * create time 2023/7/2 17:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CloneableSQLSegment extends SQLSegment{
    CloneableSQLSegment cloneSQLColumnSegment();
}
