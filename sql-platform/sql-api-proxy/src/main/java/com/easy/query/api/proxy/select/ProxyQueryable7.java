package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable7.ProxyAggregatable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyFilterable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyGroupable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyHavingable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyJoinable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyOrderable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxySelectable7;
import com.easy.query.api.proxy.select.extension.queryable7.override.OverrideProxyQueryable7;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable7<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7>
        extends OverrideProxyQueryable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyFilterable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxySelectable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyAggregatable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyGroupable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyHavingable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyOrderable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyJoinable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7> {
}
