package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable5.ProxyAggregatable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxyFilterable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxyGroupable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxyHavingable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxyJoinable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxyOrderable5;
import com.easy.query.api.proxy.select.extension.queryable5.ProxySelectable5;
import com.easy.query.api.proxy.select.extension.queryable5.override.OverrideProxyQueryable5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable5<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5>
        extends OverrideProxyQueryable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyFilterable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxySelectable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyAggregatable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyGroupable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyHavingable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyOrderable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        ProxyJoinable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5> {

}
