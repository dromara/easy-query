package com.easy.query.api.proxy.select.extension;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/8 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyAvailable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    T1Proxy getProxy();
    QueryRuntimeContext getRuntimeContext();
}
