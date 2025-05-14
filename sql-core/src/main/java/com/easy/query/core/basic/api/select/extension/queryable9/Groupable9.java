package com.easy.query.core.basic.api.select.extension.queryable9;

import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression9;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/17 21:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(SQLActionExpression9<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupBy(boolean condition, SQLActionExpression9<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>> selectExpression);

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(SQLActionExpression1<Tuple9<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> groupByMerge(boolean condition, SQLActionExpression1<Tuple9<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>, ColumnGroupSelector<T8>, ColumnGroupSelector<T9>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
