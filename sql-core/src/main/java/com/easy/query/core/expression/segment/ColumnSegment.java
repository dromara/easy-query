package com.easy.query.core.expression.segment;

import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/4/29 22:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnSegment extends SQLEntityAliasSegment {
    ColumnMetadata getColumnMetadata();
    @Override
    ColumnSegment cloneSQLColumnSegment();
}
