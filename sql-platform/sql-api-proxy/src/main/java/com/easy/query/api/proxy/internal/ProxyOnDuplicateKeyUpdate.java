package com.easy.query.api.proxy.internal;

import com.easy.query.api.proxy.sql.expression.impl.MultiColumnOnlySelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyOnDuplicateKeyUpdate<TProxy extends ProxyEntity<TProxy,T>,T, TChain> {
    TChain onConflictDoUpdate();

    TChain onConflictDoUpdate(SQLColumn<TProxy,?> constraintProperty);
    TChain onConflictDoUpdate(SQLColumn<TProxy,?> constraintProperty, SQLExpression1<MultiColumnOnlySelectorImpl<TProxy,T>> columnOnlySelector);
    TChain onConflictDoUpdate(SQLExpression1<MultiColumnOnlySelectorImpl<TProxy,T>> columnOnlySelector);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLExpression1<MultiColumnOnlySelectorImpl<TProxy,T>> columnOnlySelector);
}
