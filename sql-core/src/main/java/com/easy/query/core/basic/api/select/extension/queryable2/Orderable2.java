package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable2<T1, T2> {

    default ClientQueryable2<T1, T2> orderByAsc(SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression);

    default ClientQueryable2<T1, T2> orderByAscMerge(SQLActionExpression1<EasyTuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

   default ClientQueryable2<T1, T2> orderByAscMerge(boolean condition, SQLActionExpression1<EasyTuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression){
        return orderByAsc(condition,(t1, t2)->{
            selectExpression.apply(new EasyTuple2<>(t1, t2));
        });
   }


    default ClientQueryable2<T1, T2> orderByDesc(SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression);

    default ClientQueryable2<T1, T2> orderByDescMerge(SQLActionExpression1<EasyTuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

   default ClientQueryable2<T1, T2> orderByDescMerge(boolean condition, SQLActionExpression1<EasyTuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression){
        return orderByDesc(condition,(t1, t2)->{
            selectExpression.apply(new EasyTuple2<>(t1, t2));
        });
   }

}
