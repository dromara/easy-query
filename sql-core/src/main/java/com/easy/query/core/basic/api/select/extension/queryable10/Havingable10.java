package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/17 21:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(SQLExpression10<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>, WhereAggregatePredicate<T10>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> having(boolean condition, SQLExpression10<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>, WhereAggregatePredicate<T10>> predicateExpression);

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(SQLExpression1<Tuple10<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>, WhereAggregatePredicate<T10>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> havingMerge(boolean condition, SQLExpression1<Tuple10<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>, WhereAggregatePredicate<T8>, WhereAggregatePredicate<T9>, WhereAggregatePredicate<T10>>> predicateExpression) {
        return having(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            predicateExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}
