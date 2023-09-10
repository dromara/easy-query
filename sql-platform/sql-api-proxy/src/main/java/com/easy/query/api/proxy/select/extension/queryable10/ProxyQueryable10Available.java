package com.easy.query.api.proxy.select.extension.queryable10;

import com.easy.query.api.proxy.select.ProxyQueryable10;
import com.easy.query.api.proxy.select.extension.queryable.ProxyAvailable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable10Available<T1Proxy extends ProxyEntity<T1Proxy, T1>,T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9,
        T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> extends ProxyAvailable<T1Proxy, T1> {
    ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> getQueryable10();
    T2Proxy get2Proxy();
    T3Proxy get3Proxy();
    T4Proxy get4Proxy();
    T5Proxy get5Proxy();
    T6Proxy get6Proxy();
    T7Proxy get7Proxy();
    T8Proxy get8Proxy();
    T9Proxy get9Proxy();
    T10Proxy get10Proxy();
}
