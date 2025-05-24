package com.easy.query.core.expression.segment;

import java.util.function.Function;

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
