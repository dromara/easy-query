package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;

/**
 * create time 2026/4/25 14:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLPredicateHandler implements SQLPredicateExpression, SQLAggregatePredicateExpression {
    private final StringTypeExpression<?> stringTypeExpression;

    public SQLPredicateHandler(StringTypeExpression<?> stringTypeExpression) {
        this.stringTypeExpression = stringTypeExpression;
    }
    @Override
    public void accept(AggregateFilter f) {
        SQLFunc fx = f.getRuntimeContext().fx();
        SQLFunction notBank = fx.notBank(stringTypeExpression.func().apply(fx));
        f.sqlFunctionExecute(stringTypeExpression.getTable(), notBank);
    }

    @Override
    public void accept(Filter f) {
        SQLFunc fx = f.getRuntimeContext().fx();
        SQLFunction notBank = fx.notBank(stringTypeExpression.func().apply(fx));
        f.sqlFunctionExecute(stringTypeExpression.getTable(), notBank);
    }
}
