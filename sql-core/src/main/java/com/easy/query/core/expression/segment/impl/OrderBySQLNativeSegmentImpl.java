package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderBySQLNativeSegmentImpl extends SQLNativeSegmentImpl implements OrderBySegment {
    protected final boolean asc;

    public OrderBySQLNativeSegmentImpl(QueryRuntimeContext runtimeContext, String columnConst, SQLNativeExpression sqlNativeExpression, boolean asc){
        super(runtimeContext,columnConst,sqlNativeExpression);

        this.asc = asc;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new OrderBySQLNativeSegmentImpl(runtimeContext, sqlSegment, sqlNativeExpression,asc);
    }

    @Override
    public boolean isAsc() {
        return asc;
    }

    @Override
    public String getAlias() {
        return null;
    }
}
