package com.easy.query.core.expression.segment;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.parser.abstraction.internal.EntityTableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupColumnSegmentImpl extends ColumnSegmentImpl implements GroupByColumnSegment{
    public GroupColumnSegmentImpl(EntityTableAvailable table, String propertyName, EasyQueryRuntimeContext runtimeContext) {
        super(table, propertyName, runtimeContext);
    }

    @Override
    public OrderByColumnSegment createOrderByColumnSegment(boolean asc) {
        return new OrderColumnSegmentImpl(table,propertyName,runtimeContext,asc);
    }
}
