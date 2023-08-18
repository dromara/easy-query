package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/17 21:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable7<T1, T2, T3, T4, T5, T6, T7> {

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> having(SQLExpression7<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> having(boolean condition, SQLExpression7<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>> predicateExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(SQLExpression1<Tuple7<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> havingMerge(boolean condition, SQLExpression1<Tuple7<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>, WhereAggregatePredicate<T5>, WhereAggregatePredicate<T6>, WhereAggregatePredicate<T7>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            predicateExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

}
