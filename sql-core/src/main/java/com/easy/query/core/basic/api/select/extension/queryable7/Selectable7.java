package com.easy.query.core.basic.api.select.extension.queryable7;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.EasyTuple7;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression7;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/17 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable7<T1, T2, T3, T4, T5, T6, T7> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression7<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLActionExpression1<EasyTuple7<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6, t7) -> {
            selectExpression.apply(new EasyTuple7<>(t1, t2, t3, t4, t5, t6, t7));
        });
    }
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression7<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>> selectExpression, boolean replace);
}