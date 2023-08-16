package com.easy.query.core.basic.api.select.extension.queryable5;

import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.Tuple5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroup5Extension<T1, T2, T3, T4, T5> {


    default ClientQueryable5<T1, T2, T3, T4, T5> groupBy(SQLExpression5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable5<T1, T2, T3, T4, T5> groupBy(boolean condition, SQLExpression5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>> selectExpression);

    default ClientQueryable5<T1, T2, T3, T4, T5> groupByMerge(SQLExpression1<Tuple5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable5<T1, T2, T3, T4, T5> groupByMerge(boolean condition, SQLExpression1<Tuple5<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>>> selectExpression) {
        return groupBy(condition, (t, t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple5<>(t, t1, t2, t3, t4));
        });
    }
}
