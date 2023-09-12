package com.easy.query.api.proxy.internal;

import com.easy.query.api.proxy.sql.ProxyUpdateSetSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOnDuplicateKeyUpdate<T, TChain> {
    TChain onConflictDoUpdate();

    <TProxy extends ProxyEntity<TProxy,T>> TChain onConflictDoUpdate(TProxy tProxy,SQLColumn<TProxy,?> constraintProperty);
    <TProxy extends ProxyEntity<TProxy,T>> TChain onConflictDoUpdate(TProxy tProxy,SQLColumn<TProxy,?> constraintProperty, SQLExpression1<ProxyUpdateSetSelector> setColumnSelector);
    TChain onConflictDoUpdate(SQLExpression1<ProxyUpdateSetSelector> setColumnSelector);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLExpression1<ProxyUpdateSetSelector> setColumnSelector);
}
