package com.easy.query.api4kt.select.extension.queryable9;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;

/**
 * create time 2023/8/18 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtSelectable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends ClientKtQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    default <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression9<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>, SQLKtColumnAsSelector<T5, TR>, SQLKtColumnAsSelector<T6, TR>, SQLKtColumnAsSelector<T7, TR>, SQLKtColumnAsSelector<T8, TR>, SQLKtColumnAsSelector<T9, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable9().select(resultClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(t1), new SQLKtColumnAsSelectorImpl<>(t2), new SQLKtColumnAsSelectorImpl<>(t3), new SQLKtColumnAsSelectorImpl<>(t4), new SQLKtColumnAsSelectorImpl<>(t5), new SQLKtColumnAsSelectorImpl<>(t6), new SQLKtColumnAsSelectorImpl<>(t7), new SQLKtColumnAsSelectorImpl<>(t8),new SQLKtColumnAsSelectorImpl<>(t9));
        });
        return new EasyKtQueryable<>(select);
    }

    default <TR> KtQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple9<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>, SQLKtColumnAsSelector<T3, TR>, SQLKtColumnAsSelector<T4, TR>, SQLKtColumnAsSelector<T5, TR>, SQLKtColumnAsSelector<T6, TR>, SQLKtColumnAsSelector<T7, TR>, SQLKtColumnAsSelector<T8, TR>, SQLKtColumnAsSelector<T9, TR>>> selectExpression) {
        return select(resultClass, (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            selectExpression.apply(new Tuple9<>(t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }
}
