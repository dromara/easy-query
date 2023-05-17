package com.easy.query.core.expression.segment;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupColumnSegmentImpl extends ColumnSegmentImpl implements GroupByColumnSegment{
    public GroupColumnSegmentImpl(TableAvailable table, String propertyName, QueryRuntimeContext runtimeContext) {
        super(table, propertyName, runtimeContext);
    }

    @Override
    public OrderByColumnSegment createOrderByColumnSegment(boolean asc) {
        return new OrderColumnSegmentImpl(table,propertyName,runtimeContext,asc);
    }
}
