package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupBySQLNativeSegmentImpl extends SQLNativeSegmentImpl implements GroupByColumnSegment {

    public GroupBySQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String columnConst, SQLNativeExpressionContext sqlConstExpressionContext){
        super(runtimeContext,columnConst,sqlConstExpressionContext);

    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new GroupBySQLNativeSegmentImpl(runtimeContext, sqlSegment,sqlConstExpressionContext);
    }

    @Override
    public OrderBySegment createOrderByColumnSegment(boolean asc) {
        throw new EasyQueryInvalidOperationException("group const column cant convert order column");
    }
}
