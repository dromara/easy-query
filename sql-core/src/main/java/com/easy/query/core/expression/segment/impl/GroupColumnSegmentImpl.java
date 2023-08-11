package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupColumnSegmentImpl extends ColumnSegmentImpl implements GroupByColumnSegment {
    public GroupColumnSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, QueryRuntimeContext runtimeContext) {
        super(table, columnMetadata, runtimeContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        return runtimeContext.getSQLSegmentFactory().createOrderByColumnSegment(table,getPropertyName(),runtimeContext,asc);
    }
}
