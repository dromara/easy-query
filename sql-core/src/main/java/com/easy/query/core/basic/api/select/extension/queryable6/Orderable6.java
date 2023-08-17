package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable6<T1, T2, T3, T4, T5, T6>  {

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(SQLExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAsc(boolean condition, SQLExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(SQLExpression1<Tuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByAscMerge(boolean condition, SQLExpression1<Tuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(SQLExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDesc(boolean condition, SQLExpression6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>> selectExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(SQLExpression1<Tuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> orderByDescMerge(boolean condition, SQLExpression1<Tuple6<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}