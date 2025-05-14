package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/17 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable6<T1, T2, T3, T4, T5, T6> {

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> groupBy(SQLActionExpression6<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable6<T1, T2, T3, T4, T5, T6> groupBy(boolean condition, SQLActionExpression6<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>> selectExpression);

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> groupByMerge(SQLActionExpression1<Tuple6<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable6<T1, T2, T3, T4, T5, T6> groupByMerge(boolean condition, SQLActionExpression1<Tuple6<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}