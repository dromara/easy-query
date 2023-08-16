package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBy5Extension<T1, T2, T3, T4, T5> {

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(SQLExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(boolean condition, SQLExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByAsc(condition, (t, t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple5<>(t, t1, t2, t3, t4));
        });
    }


    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(SQLExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(boolean condition, SQLExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByDesc(condition, (t, t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple5<>(t, t1, t2, t3, t4));
        });
    }

}
