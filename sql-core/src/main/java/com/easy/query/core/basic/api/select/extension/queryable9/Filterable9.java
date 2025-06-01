package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.EasyTuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/17 21:07
 * 文件说明
 *
 * @author xuejiaming
 */

public interface Filterable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> where(boolean condition, SQLActionExpression9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>> whereExpression);


    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> whereMerge(boolean condition, SQLActionExpression1<EasyTuple9<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            whereExpression.apply(new EasyTuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}