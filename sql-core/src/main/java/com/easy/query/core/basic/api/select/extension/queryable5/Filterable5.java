package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.EasyTuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

/**
 * create time 2023/8/16 08:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filterable5<T1, T2, T3, T4, T5> {
    default ClientQueryable5<T1, T2, T3, T4, T5> where(SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> where(boolean condition, SQLActionExpression5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>> whereExpression);


    default ClientQueryable5<T1, T2, T3, T4, T5> whereMerge(SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> whereExpression) {
        return whereMerge(true, whereExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> whereMerge(boolean condition, SQLActionExpression1<EasyTuple5<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>, WherePredicate<T4>, WherePredicate<T5>>> whereExpression) {
        return where(condition, (t1, t2, t3, t4, t5) -> {
            whereExpression.apply(new EasyTuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
