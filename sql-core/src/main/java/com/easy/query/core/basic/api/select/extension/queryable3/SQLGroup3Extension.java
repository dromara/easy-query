package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLGroup3Extension<T1, T2, T3> {


    default ClientQueryable3<T1, T2, T3> groupBy(SQLExpression3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLExpression3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>> selectExpression);

    default ClientQueryable3<T1, T2, T3> groupByMerge(SQLExpression1<Tuple3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable3<T1, T2, T3> groupByMerge(boolean condition, SQLExpression1<Tuple3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>>> selectExpression) {
        return groupBy(condition, (t, t1, t2) -> {
            selectExpression.apply(new Tuple3<>(t, t1, t2));
        });
    }
}
