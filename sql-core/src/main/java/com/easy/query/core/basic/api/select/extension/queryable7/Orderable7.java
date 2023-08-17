package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable7<T1, T2, T3, T4, T5, T6, T7>{

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(SQLExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(boolean condition, SQLExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(SQLExpression1<Tuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(boolean condition, SQLExpression1<Tuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(SQLExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(boolean condition, SQLExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(SQLExpression1<Tuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(boolean condition, SQLExpression1<Tuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }
}