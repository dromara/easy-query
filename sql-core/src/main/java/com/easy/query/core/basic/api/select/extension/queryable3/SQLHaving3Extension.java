package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLHaving3Extension<T1, T2,T3> {

    default ClientQueryable3<T1, T2, T3> having(SQLExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable3<T1, T2, T3> having(boolean condition, SQLExpression3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>> predicateExpression);

    default ClientQueryable3<T1, T2, T3> havingMerge(SQLExpression1<Tuple3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable3<T1, T2, T3> havingMerge(boolean condition, SQLExpression1<Tuple3<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>, WhereAggregatePredicate<T3>>> predicateExpression){
        return having(condition,(t,t1,t2)->{
            predicateExpression.apply(new Tuple3<>(t,t1,t2));
        });
    }

}
