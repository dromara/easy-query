package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.function.Function;

/**
 * create time 2023/6/16 20:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderBySQLNativeSegment2Impl extends AbstractSQLNativeSegment2Impl implements OrderBySegment {
    protected final boolean asc;

    public OrderBySQLNativeSegment2Impl(ExpressionContext expressionContext, SQLSegment sqlSegment, Function<String,String> sqlSegmentFunction, SQLNativeExpression sqlNativeExpression, boolean asc){
        super(expressionContext,sqlSegment,sqlSegmentFunction,sqlNativeExpression);
        this.asc = asc;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return null;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new OrderBySQLNativeSegment2Impl(expressionContext, sqlSegment,sqlSegmentFunction, sqlNativeExpression,asc);
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
