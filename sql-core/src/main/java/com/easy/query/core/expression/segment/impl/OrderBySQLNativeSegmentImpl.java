package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.expression.segment.OrderBySQLNativeSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderBySQLNativeSegmentImpl extends SQLNativeSegmentImpl implements OrderBySQLNativeSegment {
    protected final boolean asc;

    public OrderBySQLNativeSegmentImpl(ExpressionContext expressionContext, String columnConst, SQLNativeExpression sqlNativeExpression, boolean asc){
        super(expressionContext,columnConst,sqlNativeExpression);

        this.asc = asc;
    }

    @Override
    public OrderBySQLNativeSegment cloneSQLColumnSegment() {
        return new OrderBySQLNativeSegmentImpl(expressionContext, sqlSegment, sqlNativeExpression,asc);
    }

    @Override
    public boolean isAsc() {
        return asc;
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public void setAlias(String alias) {
    }
}
