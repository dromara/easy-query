package com.easy.query.core.basic.api.select.extension.queryable8;

import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.EasyTuple8;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression8;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/17 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable8<T1, T2, T3, T4, T5, T6, T7, T8> {

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(SQLActionExpression8<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupBy(boolean condition, SQLActionExpression8<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>> selectExpression);

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(SQLActionExpression1<EasyTuple8<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> groupByMerge(boolean condition, SQLActionExpression1<EasyTuple8<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8) -> {
            selectExpression.apply(new EasyTuple8<>(t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }
}
