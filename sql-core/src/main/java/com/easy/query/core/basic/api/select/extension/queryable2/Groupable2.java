package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Groupable2<T1, T2> {


    default ClientQueryable2<T1, T2> groupBy(SQLActionExpression2<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ClientQueryable2<T1, T2> groupBy(boolean condition, SQLActionExpression2<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>> selectExpression);

    default ClientQueryable2<T1, T2> groupByMerge(SQLActionExpression1<EasyTuple2<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ClientQueryable2<T1, T2> groupByMerge(boolean condition, SQLActionExpression1<EasyTuple2<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>>> selectExpression) {
        return groupBy(condition, (t1, t2) -> {
            selectExpression.apply(new EasyTuple2<>(t1, t2));
        });
    }
}
