package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.extension.queryable.ProxyAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/8 21:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Proxy2Available<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ProxyAvailable<T1Proxy,T1> {

    T2Proxy get2Proxy();
}
