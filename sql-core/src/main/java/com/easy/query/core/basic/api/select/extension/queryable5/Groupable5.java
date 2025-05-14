package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable5<T1, T2, T3, T4, T5> {

    default ClientQueryable5<T1, T2, T3, T4, T5> groupBy(SQLActionExpression5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLActionExpression5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> groupByMerge(SQLActionExpression1<Tuple5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> groupByMerge(boolean condition, SQLActionExpression1<Tuple5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple5<>(t1, t2, t3, t4, t5));
        });
    }
}
