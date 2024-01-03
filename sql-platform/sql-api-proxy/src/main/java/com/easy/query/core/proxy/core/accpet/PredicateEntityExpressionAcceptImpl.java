package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2023/12/8 15:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class PredicateEntityExpressionAcceptImpl implements PredicateEntityExpressionAccept {
    private final Filter filter;
    private boolean _nextOr;

    public PredicateEntityExpressionAcceptImpl(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
        sqlPredicateExpression.accept(filter);
        if (_nextOr) {
            filter.or();
        }
    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        sqlAggregatePredicateExpression.accept(filter);
        if (_nextOr) {
            filter.or();
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public void nextOr(boolean or) {
        this._nextOr = or;
    }

    @Override
    public boolean nextIsOr() {
        return _nextOr;
    }
}
