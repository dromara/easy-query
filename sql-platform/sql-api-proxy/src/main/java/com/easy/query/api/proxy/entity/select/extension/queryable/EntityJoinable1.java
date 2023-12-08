package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/17 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {


    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(Class<T2> joinClass, SQLExpression2<T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2 extends ProxyEntityAvailable<T2,T2Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(EntityQueryable<T2Proxy, T2> joinQueryable, SQLExpression2<T1Proxy, T2Proxy> on);



}
