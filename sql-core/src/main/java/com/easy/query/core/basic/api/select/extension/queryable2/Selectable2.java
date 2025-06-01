package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.EasyTuple2;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable2<T1,T2> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLActionExpression1<EasyTuple2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>>> selectExpression) {
        return select(resultClass, (t1, t2) -> {
            selectExpression.apply(new EasyTuple2<>(t1, t2));
        });
    }

    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression, boolean replace);

}
