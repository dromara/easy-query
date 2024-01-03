package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/12/8 22:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class AggregatePredicateEntityExpressionAcceptImpl implements AggregatePredicateEntityExpressionAccept{
    private final AggregateFilter aggregateFilter;
    private boolean _nextOr;

    public AggregatePredicateEntityExpressionAcceptImpl(AggregateFilter aggregateFilter) {
        this.aggregateFilter = aggregateFilter;
    }
    @Override
    public AggregateFilter getAggregateFilter() {
        return aggregateFilter;
    }

    @Override
    public void nextOr(boolean or) {
        this._nextOr=or;
    }

    @Override
    public boolean nextIsOr() {
        return _nextOr;
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        sqlAggregatePredicateExpression.accept(aggregateFilter);
        if (_nextOr) {
            aggregateFilter.or();
        }
    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {
        throw new UnsupportedOperationException();
    }
}
