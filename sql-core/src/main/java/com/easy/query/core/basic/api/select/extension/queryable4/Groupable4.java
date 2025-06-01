package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.EasyTuple4;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable4<T1, T2, T3, T4> {


    default ClientQueryable4<T1, T2, T3,T4> groupBy(SQLActionExpression4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable4<T1, T2, T3,T4> groupBy(boolean condition, SQLActionExpression4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>> selectExpression);

    default ClientQueryable4<T1, T2, T3,T4> groupByMerge(SQLActionExpression1<EasyTuple4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable4<T1, T2, T3,T4> groupByMerge(boolean condition, SQLActionExpression1<EasyTuple4<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>, ColumnGroupSelector<T3>, ColumnGroupSelector<T4>>> selectExpression) {
        return groupBy(condition, (t1, t2,t3, t4) -> {
            selectExpression.apply(new EasyTuple4<>(t1, t2,t3, t4));
        });
    }
}
