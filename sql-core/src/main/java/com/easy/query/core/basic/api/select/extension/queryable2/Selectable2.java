package com.easy.query.core.basic.api.select.extension.queryable2;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selectable2<T1,T2> {

    <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression);

    default <TR> ClientQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>>> selectExpression) {
        return select(resultClass, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
