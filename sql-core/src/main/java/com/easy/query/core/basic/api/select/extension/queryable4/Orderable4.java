package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable4<T1, T2, T3, T4> {

    default ClientQueryable4<T1, T2, T3, T4> orderByAsc(SQLActionExpression4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLActionExpression4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>> selectExpression);

    default ClientQueryable4<T1, T2, T3, T4> orderByAscMerge(SQLActionExpression1<Tuple4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable4<T1, T2, T3, T4> orderByAscMerge(boolean condition, SQLActionExpression1<Tuple4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }


    default ClientQueryable4<T1, T2, T3, T4> orderByDesc(SQLActionExpression4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLActionExpression4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>> selectExpression);

    default ClientQueryable4<T1, T2, T3, T4> orderByDescMerge(SQLActionExpression1<Tuple4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable4<T1, T2, T3, T4> orderByDescMerge(boolean condition, SQLActionExpression1<Tuple4<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }

}
