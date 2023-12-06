package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLAssertPredicate;

/**
 * create time 2023/12/6 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLSQLFunctionAssertPredicate<TProperty> extends DSLAssertPredicate<TProperty>,DSLSQLFunctionAvailable {
    @Override
    default SQLPredicateExpression isNull(boolean condition) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.isNull(this.getTable(), func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }

    @Override
    default SQLPredicateExpression isNotNull(boolean condition) {
        if (condition) {
            return new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.isNotNull(this.getTable(), func().apply(fx));
            });
        }
        return SQLPredicateExpression.empty;
    }
}
