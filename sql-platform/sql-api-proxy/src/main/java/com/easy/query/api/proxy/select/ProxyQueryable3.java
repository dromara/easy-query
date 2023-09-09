package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable3.ProxyAggregatable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyFilterable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyGroupable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyHavingable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyJoinable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyOrderable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxySelectable3;
import com.easy.query.api.proxy.select.extension.queryable3.override.OverrideProxyQueryable3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3>
        extends OverrideProxyQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyFilterable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxySelectable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyAggregatable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyGroupable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyHavingable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyOrderable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyJoinable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3> {

}
