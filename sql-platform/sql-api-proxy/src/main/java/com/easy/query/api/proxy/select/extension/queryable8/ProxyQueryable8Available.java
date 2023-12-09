package com.easy.query.api.proxy.select.extension.queryable8;

import com.easy.query.api.proxy.select.ProxyQueryable8;
import com.easy.query.api.proxy.select.extension.queryable.Proxy1Available;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable8Available<T1Proxy extends ProxyEntity<T1Proxy, T1>,T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends Proxy1Available<T1Proxy, T1> {
    ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> getQueryable8();
    T2Proxy get2Proxy();
    T3Proxy get3Proxy();
    T4Proxy get4Proxy();
    T5Proxy get5Proxy();
    T6Proxy get6Proxy();
    T7Proxy get7Proxy();
    T8Proxy get8Proxy();
}
