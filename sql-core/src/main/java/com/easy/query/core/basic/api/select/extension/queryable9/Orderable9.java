package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(SQLExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(SQLExpression1<Tuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(boolean condition, SQLExpression1<Tuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(SQLExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(SQLExpression1<Tuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(boolean condition, SQLExpression1<Tuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new Tuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }
}