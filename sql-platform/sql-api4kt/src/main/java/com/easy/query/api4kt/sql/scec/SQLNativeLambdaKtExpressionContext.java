package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNativeLambdaKtExpressionContext<T1> {
    SQLNativeLambdaKtExpressionContext<T1> expression(KProperty1<? super T1, ?> property);

    <TEntity> SQLNativeLambdaKtExpressionContext<T1> expression(KtQueryable<TEntity> subQuery);

    <T2> SQLNativeLambdaKtExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, KProperty1<? super T2, ?> property);

    SQLNativeLambdaKtExpressionContext<T1> value(Object val);

    /**
     * 请使用 format
     *
     * @param constVal
     * @return
     */
    @Deprecated
    default SQLNativeLambdaKtExpressionContext<T1> constValue(Object constVal) {
        return format(constVal);
    }

    SQLNativeLambdaKtExpressionContext<T1> format(Object formatVal);

    SQLNativeLambdaKtExpressionContext<T1> setAlias(String alias);
}
