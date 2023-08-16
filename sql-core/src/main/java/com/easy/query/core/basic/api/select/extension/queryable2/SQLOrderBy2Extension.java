package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBy2Extension<T1, T2> {

    default ClientQueryable2<T1, T2> orderByAsc(SQLExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression);

    default ClientQueryable2<T1, T2> orderByAscMerge(SQLExpression1<Tuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

   default ClientQueryable2<T1, T2> orderByAscMerge(boolean condition, SQLExpression1<Tuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression){
        return orderByAsc(condition,(t,t1)->{
            selectExpression.apply(new Tuple2<>(t,t1));
        });
   }


    default ClientQueryable2<T1, T2> orderByDesc(SQLExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression);

    default ClientQueryable2<T1, T2> orderByDescMerge(SQLExpression1<Tuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

   default ClientQueryable2<T1, T2> orderByDescMerge(boolean condition, SQLExpression1<Tuple2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>>> selectExpression){
        return orderByDesc(condition,(t,t1)->{
            selectExpression.apply(new Tuple2<>(t,t1));
        });
   }

}
