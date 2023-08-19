package com.easy.query.api4j.select.extension.queryable3;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable3<T1, T2, T3> extends ClientQueryable3Available<T1, T2, T3> {

    default <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable3().select(resultClass, (t1, t2,t3) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(t1), new SQLColumnAsSelectorImpl<>(t2),new SQLColumnAsSelectorImpl<>(t3));
        });
        return new EasyQueryable<>(select);
    }

    default <TR> Queryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple3<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>>> selectExpression) {
        return select(resultClass, (t1, t2,t3) -> {
            selectExpression.apply(new Tuple3<>(t1, t2,t3));
        });
    }
}
