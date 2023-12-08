package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/8 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLEntityNativeSegmentImpl implements SQLOrderByExpression, SQLPredicateExpression, SQLAggregatePredicateExpression {
    private final String sqlSegment;
    private final SQLExpression1<SQLNativeProxyExpressionContext> contextConsume;

    public SQLEntityNativeSegmentImpl(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        this.sqlSegment = sqlSegment;
        this.contextConsume = contextConsume;
    }
    @Override
    public void accept(AggregateFilter f) {
        f.sqlNativeSegment(sqlSegment,c->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }

    @Override
    public void accept(OrderSelector s) {
        s.sqlNativeSegment(sqlSegment,c->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }

    @Override
    public void accept(Filter f) {
        f.sqlNativeSegment(sqlSegment,c->{
            contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
        });
    }
}
