package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.EasyTuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable6<T1, T2, T3, T4, T5, T6> {
    default ClientQueryable6<T1, T2, T3, T4, T5, T6> where(SQLActionExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> where(boolean condition, SQLActionExpression6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>> whereExpression);


    default ClientQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(SQLActionExpression1<EasyTuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> whereMerge(boolean condition, SQLActionExpression1<EasyTuple6<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6) -> {
            whereExpression.apply(new EasyTuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}