package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable2.ProxyAggregatable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyFilterable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyGroupable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyHavingable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyJoinable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxyOrderable2;
import com.easy.query.api.proxy.select.extension.queryable2.ProxySelectable2;
import com.easy.query.api.proxy.select.extension.queryable2.override.OverrideProxyQueryable2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends OverrideProxyQueryable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyFilterable2<T1Proxy,T1,T2Proxy, T2>,
        ProxySelectable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyAggregatable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyGroupable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyHavingable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyOrderable2<T1Proxy,T1,T2Proxy, T2>,
        ProxyJoinable2<T1Proxy,T1,T2Proxy, T2> {

}
