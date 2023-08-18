package com.easy.query.api4kt.select.extension.queryable2;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.select.impl.EasyKtQueryable;
import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtSelectable2<T1, T2> extends ClientKtQueryable2Available<T1, T2> {

    default <TR> KtQueryable<TR> select(Class<TR> resultClass, SQLExpression2<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable2().select(resultClass, (t1, t2) -> {
            selectExpression.apply(new SQLKtColumnAsSelectorImpl<>(t1),new SQLKtColumnAsSelectorImpl<>(t2));
        });
        return new EasyKtQueryable<>(select);
    }

    default <TR> KtQueryable<TR> selectMerge(Class<TR> resultClass, SQLExpression1<Tuple2<SQLKtColumnAsSelector<T1, TR>, SQLKtColumnAsSelector<T2, TR>>> selectExpression) {
        return select(resultClass, (t1, t2) -> {
            selectExpression.apply(new Tuple2<>(t1, t2));
        });
    }
}
