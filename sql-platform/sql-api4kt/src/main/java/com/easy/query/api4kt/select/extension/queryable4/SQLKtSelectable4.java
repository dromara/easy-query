package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtSelectable4<T1, T2, T3,T4> extends ClientKtQueryable4Available<T1, T2, T3,T4> {

    default <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression4<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable4().select(resultClass, (t1, t2, t3, t4) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(t1), new SQLKtColumnAsSelectorImpl<>(t2), new SQLKtColumnAsSelectorImpl<>(t3),new SQLKtColumnAsSelectorImpl<>(t4));
        });
        return new EasyKtQueryable<>(select);
    }

    default <TR> KtQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple4<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4) -> {
            selectExpression.apply(new Tuple4<>(t1, t2, t3, t4));
        });
    }
}
