package com.easy.query.core.basic.api.select.extension.queryable4;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.EasyTuple4;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression4;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable4<T1,T2,T3,T4> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression4<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLActionExpression1<EasyTuple4<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>>> selectExpression) {
        return select(resultClass, (t, t1,t2,t3) -> {
            selectExpression.apply(new EasyTuple4<>(t, t1,t2,t3));
        });
    }
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression4<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>, ColumnAsSelector<T3, TR>, ColumnAsSelector<T4, TR>> selectExpression, boolean replace);
}
