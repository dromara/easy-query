package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.EasyTuple7;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression7;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/17 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable7<T1, T2, T3, T4, T5, T6, T7>{

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(SQLActionExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAsc(boolean condition, SQLActionExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(SQLActionExpression1<EasyTuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByAscMerge(boolean condition, SQLActionExpression1<EasyTuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new EasyTuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(SQLActionExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDesc(boolean condition, SQLActionExpression7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>> selectExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(SQLActionExpression1<EasyTuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> orderByDescMerge(boolean condition, SQLActionExpression1<EasyTuple7<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>, ColumnOrderSelector<T6>, ColumnOrderSelector<T7>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new EasyTuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}