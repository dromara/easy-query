package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable6<T1, T2, T3, T4, T5, T6> {
    default ClientQueryable6<T1, T2, T3, T4, T5, T6> where(SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> where(boolean condition, SQLExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> whereExpression);


    default ClientQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(boolean condition, SQLExpression1<Tuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> whereExpression) {
        return where(condition, (t, t1, t2, t3, t4, t5) -> {
            whereExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}