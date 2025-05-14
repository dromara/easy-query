package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Orderable5<T1, T2, T3, T4, T5> {

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(SQLActionExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> orderByAsc(boolean condition, SQLActionExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(SQLActionExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByAscMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByAscMerge(boolean condition, SQLActionExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByAsc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }


    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(SQLActionExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> orderByDesc(boolean condition, SQLActionExpression5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(SQLActionExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByDescMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> orderByDescMerge(boolean condition, SQLActionExpression1<Tuple5<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>, ColumnOrderSelector<T3>, ColumnOrderSelector<T4>, ColumnOrderSelector<T5>>> selectExpression) {
        return orderByDesc(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }

}
