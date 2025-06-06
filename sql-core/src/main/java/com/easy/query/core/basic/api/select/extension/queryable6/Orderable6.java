package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.EasyTuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable6<T1, T2, T3, T4, T5, T6>  {

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(SQLActionExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(boolean condition, SQLActionExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(SQLActionExpression1<EasyTuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(boolean condition, SQLActionExpression1<EasyTuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new EasyTuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(SQLActionExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(boolean condition, SQLActionExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(SQLActionExpression1<EasyTuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(boolean condition, SQLActionExpression1<EasyTuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new EasyTuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}