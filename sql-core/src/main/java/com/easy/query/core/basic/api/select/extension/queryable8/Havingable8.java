package com.easy.query.core.basic.api.select.extension.queryable8;

import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.EasyTuple8;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression8;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/17 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable8<T1, T2, T3, T4, T5, T6, T7, T8> {

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(SQLActionExpression8<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> having(boolean condition, SQLActionExpression8<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>> predicateExpression);

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(SQLActionExpression1<EasyTuple8<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> havingMerge(boolean condition, SQLActionExpression1<EasyTuple8<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            predicateExpression.apply(new EasyTuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

}
