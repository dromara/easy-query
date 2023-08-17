package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/17 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> where(SQLExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> where(boolean condition, SQLExpression10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>> whereExpression);


    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereMerge(SQLExpression1<Tuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> whereMerge(boolean condition, SQLExpression1<Tuple10<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>, WherePredicate<T8>, WherePredicate<T9>, WherePredicate<T10>>> whereExpression) {
        return where(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            whereExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}