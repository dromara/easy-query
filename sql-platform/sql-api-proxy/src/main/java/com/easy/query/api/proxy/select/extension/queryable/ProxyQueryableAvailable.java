package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryableAvailable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ProxyAvailable<T1Proxy,T1>{
    ProxyQueryable<T1Proxy, T1> getQueryable();
    QueryRuntimeContext getRuntimeContext();
}
