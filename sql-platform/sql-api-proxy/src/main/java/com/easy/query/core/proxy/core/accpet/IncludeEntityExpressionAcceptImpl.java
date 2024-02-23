package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;

/**
 * create time 2024/2/23 11:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludeEntityExpressionAcceptImpl implements IncludeEntityExpressionAccept{
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
        throw new UnsupportedOperationException();
    }
}
