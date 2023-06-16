package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupColumnSegmentImpl extends ColumnSegmentImpl implements GroupByColumnSegment {
    public GroupColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        super(table, propertyName, runtimeContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        return runtimeContext.getSQLSegmentFactory().createOrderByColumnSegment(table,propertyName,runtimeContext,asc);
    }
}
