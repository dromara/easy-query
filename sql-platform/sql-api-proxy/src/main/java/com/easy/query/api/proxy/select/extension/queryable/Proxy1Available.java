package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/10 14:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Proxy1Available<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    T1Proxy get1Proxy();
    QueryRuntimeContext getRuntimeContext();
}
