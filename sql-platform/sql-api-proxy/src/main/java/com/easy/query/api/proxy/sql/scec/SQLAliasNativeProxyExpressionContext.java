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
public interface SQLAliasNativeProxyExpressionContext<TRProxy extends ProxyEntity<TRProxy, TR>, TR> extends SQLNativeProxyExpressionContext{
    <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn);

    @Override
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(SQLColumn<TEntityProxy, TProperty> sqlColumn);

    @Override
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(ProxyQueryable<TEntityProxy, TEntity> subQuery);

    @Override
    SQLAliasNativeProxyExpressionContext<TRProxy, TR> value(Object val);

    @Override
    SQLAliasNativeProxyExpressionContext<TRProxy, TR> format(Object formatVal);

    @Override
    SQLAliasNativeProxyExpressionContext<TRProxy, TR> setAlias(String alias);
}
