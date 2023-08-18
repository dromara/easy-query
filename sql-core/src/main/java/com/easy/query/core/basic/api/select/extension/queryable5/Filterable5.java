package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable5<T1, T2, T3, T4, T5> {
    default ClientQueryable5<T1, T2, T3, T4, T5> where(SQLExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> whereExpression);


    default ClientQueryable5<T1, T2, T3, T4, T5> whereMerge(SQLExpression1<Tuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> whereMerge(boolean condition, SQLExpression1<Tuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5) -> {
            whereExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
