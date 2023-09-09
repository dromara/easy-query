package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyNavigateInclude;
import com.easy.query.api.proxy.sql.impl.ProxyNavigateIncludeImpl;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyIncludeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>, ProxyQueryableAvailable<T1Proxy, T1> {

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<T1Proxy, T1> include(SQLFuncExpression2<ProxyNavigateInclude<T1>,T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<T1Proxy,T1> include(boolean condition, SQLFuncExpression2<ProxyNavigateInclude<T1>,T1Proxy, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new ProxyNavigateIncludeImpl<>(navigateInclude),getQueryable().get1Proxy()).getClientQueryable());
        }
        return getQueryable();
    }
}
