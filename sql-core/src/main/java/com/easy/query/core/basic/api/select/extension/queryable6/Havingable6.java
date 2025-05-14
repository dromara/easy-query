package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/17 21:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable6<T1, T2, T3, T4, T5, T6> {

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> having(SQLActionExpression6<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> having(boolean condition, SQLActionExpression6<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>> predicateExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> havingMerge(SQLActionExpression1<Tuple6<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> havingMerge(boolean condition, SQLActionExpression1<Tuple6<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6) -> {
            predicateExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

}
