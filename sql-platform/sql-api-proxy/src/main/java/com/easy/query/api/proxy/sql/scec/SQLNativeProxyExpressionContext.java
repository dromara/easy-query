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
public interface SQLNativeProxyExpressionContext {
    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLNativeProxyExpressionContext expression(SQLColumn<TEntityProxy, TProperty> sqlColumn);

    <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLNativeProxyExpressionContext expression(ProxyQueryable<TEntityProxy, TEntity> subQuery);

    SQLNativeProxyExpressionContext value(Object val);

    SQLNativeProxyExpressionContext format(Object formatVal);

    SQLNativeProxyExpressionContext setAlias(String alias);
}
