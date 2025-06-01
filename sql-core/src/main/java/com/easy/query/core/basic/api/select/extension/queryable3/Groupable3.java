package com.easy.query.core.basic.api.select.extension.queryable3;

import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.EasyTuple3;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression3;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable3<T1, T2, T3> {


    default ClientQueryable3<T1, T2, T3> groupBy(SQLActionExpression3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable3<T1, T2, T3> groupBy(boolean condition, SQLActionExpression3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>> selectExpression);

    default ClientQueryable3<T1, T2, T3> groupByMerge(SQLActionExpression1<EasyTuple3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable3<T1, T2, T3> groupByMerge(boolean condition, SQLActionExpression1<EasyTuple3<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>>> selectExpression) {
        return groupBy(condition, (t1, t2,t3) -> {
            selectExpression.apply(new EasyTuple3<>(t1, t2,t3));
        });
    }
}
