package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Havingable2<T1, T2> {

    default ClientQueryable2<T1, T2> having(SQLActionExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression) {
        return having(true, predicateExpression);
    }

    ClientQueryable2<T1, T2> having(boolean condition, SQLActionExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression);

    default ClientQueryable2<T1, T2> havingMerge(SQLActionExpression1<EasyTuple2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ClientQueryable2<T1, T2> havingMerge(boolean condition, SQLActionExpression1<EasyTuple2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>>> predicateExpression){
        return having(condition,(t1, t2)->{
            predicateExpression.apply(new EasyTuple2<>(t1, t2));
        });
    }

}
