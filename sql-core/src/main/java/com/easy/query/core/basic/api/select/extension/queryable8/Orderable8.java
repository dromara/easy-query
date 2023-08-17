package com.easy.query.core.basic.api.select.extension.queryable8;

import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable8<T1, T2, T3, T4, T5, T6, T7, T8> {

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(SQLExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAsc(boolean condition, SQLExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression);

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(SQLExpression1<Tuple8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByAscMerge(boolean condition, SQLExpression1<Tuple8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(SQLExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDesc(boolean condition, SQLExpression8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>> selectExpression);

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(SQLExpression1<Tuple8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> orderByDescMerge(boolean condition, SQLExpression1<Tuple8<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }
}