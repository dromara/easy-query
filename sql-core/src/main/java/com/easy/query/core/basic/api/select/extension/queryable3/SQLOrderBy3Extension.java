package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBy3Extension<T1, T2, T3> {

    default ClientQueryable3<T1, T2, T3> orderByAsc(SQLExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> orderByAsc(boolean condition, SQLExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression);

    default ClientQueryable3<T1, T2, T3> orderByAscMerge(SQLExpression1<Tuple3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

   default ClientQueryable3<T1, T2, T3> orderByAscMerge(boolean condition, SQLExpression1<Tuple3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>>> selectExpression){
        return orderByAsc(condition,(t,t1,t2)->{
            selectExpression.apply(new Tuple3<>(t,t1,t2));
        });
   }


    default ClientQueryable3<T1, T2, T3> orderByDesc(SQLExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> orderByDesc(boolean condition, SQLExpression3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>> selectExpression);

    default ClientQueryable3<T1, T2, T3> orderByDescMerge(SQLExpression1<Tuple3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

   default ClientQueryable3<T1, T2, T3> orderByDescMerge(boolean condition, SQLExpression1<Tuple3<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>>> selectExpression){
        return orderByDesc(condition,(t,t1,t2)->{
            selectExpression.apply(new Tuple3<>(t,t1,t2));
        });
   }

}
