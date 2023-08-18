package com.easy.query.core.basic.api.select.extension.queryable10;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression10;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/17 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression10<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>, ColumnAsSelector<T9, TR>, ColumnAsSelector<T10, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple10<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>, ColumnAsSelector<T5, TR>, ColumnAsSelector<T6, TR>, ColumnAsSelector<T7, TR>, ColumnAsSelector<T8, TR>, ColumnAsSelector<T9, TR>, ColumnAsSelector<T10, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9, t10) -> {
            selectExpression.apply(new Tuple10<>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10));
        });
    }
}