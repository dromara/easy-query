package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable9.ProxyAggregatable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxyFilterable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxyGroupable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxyHavingable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxyJoinable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxyOrderable9;
import com.easy.query.api.proxy.select.extension.queryable9.ProxySelectable9;
import com.easy.query.api.proxy.select.extension.queryable9.override.OverrideProxyQueryable9;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable9<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9>
        extends OverrideProxyQueryable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyFilterable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxySelectable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyAggregatable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyGroupable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyHavingable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyOrderable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9>,
        ProxyJoinable9<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7,T8Proxy, T8,T9Proxy, T9> {
}
