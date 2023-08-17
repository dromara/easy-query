package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable4<T1, T2, T3, T4> {


    default ClientQueryable4<T1, T2, T3,T4> groupBy(SQLExpression4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> groupBy(boolean condition, SQLExpression4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>> selectExpression);

    default ClientQueryable4<T1, T2, T3,T4> groupByMerge(SQLExpression1<Tuple4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> groupByMerge(boolean condition, SQLExpression1<Tuple4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>>> selectExpression) {
        return groupBy(condition, (t, t1, t2,t3) -> {
            selectExpression.apply(new Tuple4<>(t, t1, t2,t3));
        });
    }
}
