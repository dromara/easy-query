package com.easy.query.api.proxy.internal;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectExpression;

/**
 * create time 2023/7/7 08:07
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyEntityOnDuplicateKeyUpdate<TProxy extends ProxyEntity<TProxy,T>,T, TChain> {
    TChain onConflictDoUpdate();

    TChain onConflictDoUpdate(SQLFuncExpression1<TProxy,SQLColumn<TProxy,?>> constraintPropertyExpression);
    TChain onConflictDoUpdate(SQLFuncExpression1<TProxy,SQLColumn<TProxy,?>> constraintPropertyExpression, SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression);

    TChain onDuplicateKeyUpdate();

    TChain onDuplicateKeyUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression);
}
