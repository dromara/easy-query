package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.EasyTuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/17 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(SQLActionExpression9<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> having(boolean condition, SQLActionExpression9<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>> predicateExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> havingMerge(SQLActionExpression1<EasyTuple9<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> havingMerge(boolean condition, SQLActionExpression1<EasyTuple9<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            predicateExpression.apply(new EasyTuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}