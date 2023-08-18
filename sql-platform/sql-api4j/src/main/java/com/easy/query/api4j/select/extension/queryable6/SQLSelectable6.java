package com.easy.query.api4j.select.extension.queryable6;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;

/**
 * create time 2023/8/18 09:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSelectable6<T1, T2, T3, T4, T5, T6> extends ClientQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default <TR> Queryable<TR> select(Class<TR> resultClass, SQLExpression6<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable6().select(resultClass, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new SQLColumnAsSelectorImpl<>(t), new SQLColumnAsSelectorImpl<>(t1), new SQLColumnAsSelectorImpl<>(t2), new SQLColumnAsSelectorImpl<>(t3), new SQLColumnAsSelectorImpl<>(t4), new SQLColumnAsSelectorImpl<>(t5));
        });
        return new EasyQueryable<>(select);
    }

    default <TR> Queryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple6<SQLColumnAsSelector<T1, TR>, SQLColumnAsSelector<T2, TR>, SQLColumnAsSelector<T3, TR>, SQLColumnAsSelector<T4, TR>, SQLColumnAsSelector<T5, TR>, SQLColumnAsSelector<T6, TR>>> selectExpression) {
        return select(resultClass, (t, t1, t2, t3, t4, t5) -> {
            selectExpression.apply(new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }
}
