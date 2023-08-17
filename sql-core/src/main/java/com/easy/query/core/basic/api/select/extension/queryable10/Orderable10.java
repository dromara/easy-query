package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(SQLExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAsc(boolean condition, SQLExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression);

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(SQLExpression1<Tuple10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByAscMerge(boolean condition, SQLExpression1<Tuple10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(SQLExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDesc(boolean condition, SQLExpression10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>> selectExpression);

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(SQLExpression1<Tuple10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> orderByDescMerge(boolean condition, SQLExpression1<Tuple10<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>, ColumnOrderSelector<T10>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}