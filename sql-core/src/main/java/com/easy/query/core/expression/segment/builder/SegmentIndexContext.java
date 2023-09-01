package com.easy.query.core.expression.segment.builder;

import com.easy.query.core.expression.segment.SQLEntitySegment;

/**
 * create time 2023/9/1 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SegmentIndexContext {
    void add(SQLEntitySegment sqlEntitySegment);
}
