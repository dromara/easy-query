package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable2<T1, T2> {

    default ClientQueryable2<T1, T2> having(SQLExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable2<T1, T2> having(boolean condition, SQLExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression);

    default ClientQueryable2<T1, T2> havingMerge(SQLExpression1<Tuple2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable2<T1, T2> havingMerge(boolean condition, SQLExpression1<Tuple2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>>> predicateExpression){
        return having(condition,(t1, t2)->{
            predicateExpression.apply(new Tuple2<>(t1, t2));
        });
    }

}
