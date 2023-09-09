package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable6.ProxyAggregatable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyFilterable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyGroupable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyHavingable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyJoinable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxyOrderable6;
import com.easy.query.api.proxy.select.extension.queryable6.ProxySelectable6;
import com.easy.query.api.proxy.select.extension.queryable6.override.OverrideProxyQueryable6;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable6<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6>
        extends OverrideProxyQueryable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyFilterable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxySelectable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyAggregatable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyGroupable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyHavingable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyOrderable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        ProxyJoinable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6> {
}
