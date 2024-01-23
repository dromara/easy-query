package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityAggregatable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityFilterable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityGroupable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityJoinable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntityOrderable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.EntitySelectable4;
import com.easy.query.api.proxy.entity.select.extension.queryable4.override.OverrideEntityQueryable4;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> 
        extends OverrideEntityQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntityFilterable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntitySelectable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntityAggregatable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntityGroupable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
//        EntityHavingable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntityJoinable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        EntityOrderable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4> {
}
