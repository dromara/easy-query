package com.easy.query.api.proxy.sql.scec;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAliasNativeProxyExpressionContext extends SQLNativeProxyExpressionContext{
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR, TProperty> SQLAliasNativeProxyExpressionContext expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn);

    @Override
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext expression(SQLColumn<TEntityProxy, TProperty> sqlColumn);

    @Override
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLAliasNativeProxyExpressionContext expression(ProxyQueryable<TEntityProxy, TEntity> subQuery);

    @Override
    SQLAliasNativeProxyExpressionContext value(Object val);

    @Override
    SQLAliasNativeProxyExpressionContext format(Object formatVal);

    @Override
    SQLAliasNativeProxyExpressionContext setAlias(String alias);
}
