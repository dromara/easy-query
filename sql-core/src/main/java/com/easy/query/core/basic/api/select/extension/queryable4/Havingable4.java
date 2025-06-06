package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.EasyTuple4;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable4<T1, T2, T3, T4> {

    default ClientQueryable4<T1, T2, T3,T4> having(SQLActionExpression4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> having(boolean condition, SQLActionExpression4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>> predicateExpression);

    default ClientQueryable4<T1, T2, T3,T4> havingMerge(SQLActionExpression1<EasyTuple4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> havingMerge(boolean condition, SQLActionExpression1<EasyTuple4<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>, WhereAggregatePredicate<T4>>> predicateExpression) {
        return having(condition, (t1, t2, t3, t4) -> {
            predicateExpression.apply(new EasyTuple4<>(t1, t2, t3, t4));
        });
    }

}
