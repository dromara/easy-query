package com.easy.query.api4kt.select.extension.queryable6;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
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
public interface SQLKtSelectable6<T1, T2, T3, T4, T5, T6> extends ClientKtQueryable6Available<T1, T2, T3, T4, T5, T6> {

    default <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression6<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>, SQLKtColumnAsSelector<T5, TR>, SQLKtColumnAsSelector<T6, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable6().select(resultClass, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(t1), new SQLKtColumnAsSelectorImpl<>(t2), new SQLKtColumnAsSelectorImpl<>(t3), new SQLKtColumnAsSelectorImpl<>(t4), new SQLKtColumnAsSelectorImpl<>(t5),new SQLKtColumnAsSelectorImpl<>(t6));
        });
        return new EasyKtQueryable<>(select);
    }

    default <TR> KtQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple6<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>, SQLKtColumnAsSelector<T5, TR>, SQLKtColumnAsSelector<T6, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6) -> {
            selectExpression.apply(new Tuple6<>(t1, t2, t3, t4, t5, t6));
        });
    }
}
