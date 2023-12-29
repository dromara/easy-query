package com.easy.query.core.proxy.core.accpet;

import com.easy.query.core.expression.builder.Setter;
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
public class SetterEntityExpressionAcceptImpl implements SetterEntityExpressionAccept {
    private final Setter setter;

    public SetterEntityExpressionAcceptImpl(Setter setter) {
        this.setter = setter;
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
        sqlColumnSetExpression.accept(setter);
    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Setter getSetter() {
        return setter;
    }
}
