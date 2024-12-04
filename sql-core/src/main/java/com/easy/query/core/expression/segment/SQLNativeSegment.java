package com.easy.query.core.expression.segment;

/**
 * create time 2023/6/16 21:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeSegment extends SQLEntityAliasSegment{
    @Override
    SQLNativeSegment cloneSQLColumnSegment();
}
