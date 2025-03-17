package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2025/3/12 23:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubquerySQLQueryableFactory {
    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> SQLQueryable<T1Proxy, T1> create(SubQueryContext<T1Proxy, T1> subqueryContext);
    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(SubQueryContext<T1Proxy, T1> subqueryContext,int index);
//    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(GroupingQueryContext<T1Proxy, T1> subqueryContext,int index);
}
