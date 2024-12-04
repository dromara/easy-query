package com.easy.query.core.expression.segment;

import com.easy.query.core.expression.segment.impl.SQLNativeSegmentImpl;

/**
 * create time 2024/12/3 13:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupBySQLNativeSegment extends SQLNativeSegment, GroupByColumnSegment {
    @Override
    GroupBySQLNativeSegment cloneSQLColumnSegment();
}
