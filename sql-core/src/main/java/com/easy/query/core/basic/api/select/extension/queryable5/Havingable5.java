package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable5<T1, T2, T3, T4, T5> {

    default ClientQueryable5<T1, T2, T3, T4, T5> having(SQLExpression5<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> having(boolean condition, SQLExpression5<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>> predicateExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> havingMerge(SQLExpression1<Tuple5<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> havingMerge(boolean condition, SQLExpression1<Tuple5<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5) -> {
            predicateExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

}
