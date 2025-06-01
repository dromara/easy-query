package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.EasyTuple6;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression6;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/17 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable6<T1, T2, T3, T4, T5, T6> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression6<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLActionExpression1<EasyTuple6<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new EasyTuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression6<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>> selectExpression, boolean replace);
}
