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
public interface SQLAliasNativeProxyExpressionContext<TRProxy extends ProxyEntity<TRProxy, TR>, TR> extends SQLNativeProxyExpressionChain<SQLAliasNativeProxyExpressionContext<TRProxy,TR>>{
    <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn);
    <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> setPropertyAlias(SQLColumn<TRProxy, TProperty> sqlColumn);
}
