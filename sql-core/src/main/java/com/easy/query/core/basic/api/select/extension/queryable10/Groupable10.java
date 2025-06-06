package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.EasyTuple10;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression10;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/17 21:13
 * 文件说明
 *
 * @author xuejiaming
 */

public interface Groupable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(SQLActionExpression10<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>, ColumnGroupSelector<T10>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupBy(boolean condition, SQLActionExpression10<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>, ColumnGroupSelector<T10>> selectExpression);

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(SQLActionExpression1<EasyTuple10<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>, ColumnGroupSelector<T10>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> groupByMerge(boolean condition, SQLActionExpression1<EasyTuple10<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>, ColumnGroupSelector<T10>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new EasyTuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}
