package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable4<T1, T2, T3, T4> {

    default ClientQueryable4<T1, T2, T3,T4> having(SQLExpression4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> having(boolean condition, SQLExpression4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>> predicateExpression);

    default ClientQueryable4<T1, T2, T3,T4> havingMerge(SQLExpression1<Tuple4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> havingMerge(boolean condition, SQLExpression1<Tuple4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4) -> {
            predicateExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

}
