package com.easy.query.api.proxy.internal;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityOnDuplicateKeyUpdate<TProxy extends ProxyEntity<TProxy,T>,T, TChain> {
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate();

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate(SQLFuncExpression1<TProxy,SQLSelectExpression> constraintPropertyExpression);
    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onConflictDoUpdate(SQLFuncExpression1<TProxy,SQLSelectExpression> constraintPropertyExpression, SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression);

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onDuplicateKeyUpdate();

    /**
     * 请使用 onConflictThen
     * @return
     */
    @Deprecated
    TChain onDuplicateKeyUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression);
}
