package com.easy.query.core.basic.api.select.extension.queryable8;

import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.EasyTuple8;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression8;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/17 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable8<T1, T2, T3, T4, T5, T6, T7, T8> {
    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(SQLActionExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> where(boolean condition, SQLActionExpression8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>> whereExpression);


    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(SQLActionExpression1<EasyTuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> whereMerge(boolean condition, SQLActionExpression1<EasyTuple8<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            whereExpression.apply(new EasyTuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }
}