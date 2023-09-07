package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {


    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy joinProxy, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);

    <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression3<ProxyFilter, T1Proxy, T2Proxy> on);



}
