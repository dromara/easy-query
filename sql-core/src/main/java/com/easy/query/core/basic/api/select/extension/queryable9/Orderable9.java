package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.EasyTuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(SQLActionExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAsc(boolean condition, SQLActionExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(SQLActionExpression1<EasyTuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByAscMerge(boolean condition, SQLActionExpression1<EasyTuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new EasyTuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(SQLActionExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDesc(boolean condition, SQLActionExpression9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>> selectExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(SQLActionExpression1<EasyTuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> orderByDescMerge(boolean condition, SQLActionExpression1<EasyTuple9<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>, ColumnOrderSelector<T8>, ColumnOrderSelector<T9>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new EasyTuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}