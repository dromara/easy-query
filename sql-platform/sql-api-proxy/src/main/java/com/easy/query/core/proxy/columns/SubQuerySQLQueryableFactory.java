package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2025/3/12 23:32
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SubQuerySQLQueryableFactory {
    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> SQLQueryable<T1Proxy, T1> create(SubQueryContext<T1Proxy, T1> subQueryContext);
    <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> T1Proxy create(SubQueryContext<T1Proxy, T1> subQueryContext,int index);
}
