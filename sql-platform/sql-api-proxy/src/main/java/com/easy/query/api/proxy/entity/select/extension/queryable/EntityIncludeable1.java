package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityIncludeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty  extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty  extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<ProxyEntityNavigateInclude<T1,T1Proxy>, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        return include(true, navigateIncludeSQLExpression);
//    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty,TPropertyProxy>> EntityQueryable<T1Proxy,T1> include(boolean condition, SQLFuncExpression1<T1Proxy, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            ClientQueryable<TProperty> clientQueryable = navigateIncludeSQLExpression.apply(getQueryable().get1Proxy());
//            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new ProxyEntityNavigateIncludeImpl<>(getQueryable().get1Proxy(),navigateInclude)).getClientQueryable());
        }
        return getQueryable();
    }
}
