package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupByConstSegmentImpl extends ColumnConstSegmentImpl implements GroupByColumnSegment {

    public GroupByConstSegmentImpl(QueryRuntimeContext runtimeContext, String columnConst, SQLConstExpressionContext sqlConstExpressionContext){
        super(runtimeContext,columnConst,sqlConstExpressionContext);

    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new GroupByConstSegmentImpl(runtimeContext,columnConst,sqlConstExpressionContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        throw new EasyQueryInvalidOperationException("group const column cant convert order column");
    }
}
