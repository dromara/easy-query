package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression7;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/17 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable7<T1, T2, T3, T4, T5, T6, T7> {

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(SQLActionExpression7<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> groupBy(boolean condition, SQLActionExpression7<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>> selectExpression);

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(SQLActionExpression1<Tuple7<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> groupByMerge(boolean condition, SQLActionExpression1<Tuple7<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>, ColumnGroupSelector<T5>, ColumnGroupSelector<T6>, ColumnGroupSelector<T7>>> selectExpression) {
        return groupBy(condition, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new Tuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
}
