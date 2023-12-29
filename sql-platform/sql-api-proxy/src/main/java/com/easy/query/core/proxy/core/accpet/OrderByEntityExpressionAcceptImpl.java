package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/12/8 17:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderByEntityExpressionAcceptImpl implements OrderByEntityExpressionAccept {
    private final OrderSelector orderSelector;

    public OrderByEntityExpressionAcceptImpl(OrderSelector orderSelector) {
        this.orderSelector = orderSelector;
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {
        sqlOrderByExpression.accept(orderSelector);
    }

    @Override
    public OrderSelector getOrderSelector() {
        return orderSelector;
    }
}
