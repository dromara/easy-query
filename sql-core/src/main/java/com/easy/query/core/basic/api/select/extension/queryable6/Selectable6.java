package com.easy.query.core.basic.api.select.extension.queryable6;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/17 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable6<T1, T2, T3, T4, T5, T6> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression6<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple6<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}
