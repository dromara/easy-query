package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable4.ProxyAggregatable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyFilterable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyGroupable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyHavingable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyJoinable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyOrderable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxySelectable4;
import com.easy.query.api.proxy.select.extension.queryable4.override.OverrideProxyQueryable4;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> 
        extends OverrideProxyQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyFilterable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxySelectable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyAggregatable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyGroupable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyHavingable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyOrderable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyJoinable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4> {
}
