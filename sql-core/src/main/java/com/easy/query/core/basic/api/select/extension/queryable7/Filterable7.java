package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.EasyTuple7;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression7;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable7<T1, T2, T3, T4, T5, T6, T7> {
    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> where(SQLActionExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> where(boolean condition, SQLActionExpression7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>> whereExpression);


    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(SQLActionExpression1<EasyTuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> whereMerge(boolean condition, SQLActionExpression1<EasyTuple7<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>, WherePredicate<T6>, WherePredicate<T7>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            whereExpression.apply(new EasyTuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}