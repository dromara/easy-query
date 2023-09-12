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
public interface SQLAliasNativeLambdaKtExpressionContext<T1,TR> extends SQLNativeLambdaKtExpressionContext<T1> {

    SQLAliasNativeLambdaKtExpressionContext<T1,TR> expressionAlias(KProperty1<? super TR, ?> property);

    @Override
    SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(KProperty1<? super T1, ?> property);


    @Override
    <TEntity> SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(KtQueryable<TEntity> subQuery);


    @Override
    <T2> SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(EntitySQLTableOwner<T2> table, KProperty1<? super T2, ?> property);



    @Override
    SQLAliasNativeLambdaKtExpressionContext<T1,TR> value(Object val);

    /**
     * 请使用 format
     *
     * @param constVal
     * @return
     */
    @Deprecated

    @Override
    default SQLAliasNativeLambdaKtExpressionContext<T1,TR> constValue(Object constVal) {
        return format(constVal);
    }


    @Override
    SQLAliasNativeLambdaKtExpressionContext<T1,TR> format(Object formatVal);


    @Override
    SQLAliasNativeLambdaKtExpressionContext<T1,TR> setAlias(String alias);
}
