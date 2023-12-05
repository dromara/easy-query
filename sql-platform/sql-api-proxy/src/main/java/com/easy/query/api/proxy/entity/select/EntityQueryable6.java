package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityAggregatable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityFilterable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityGroupable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityHavingable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityJoinable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntityOrderable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.EntitySelectable6;
import com.easy.query.api.proxy.entity.select.extension.queryable6.override.OverrideEntityQueryable6;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable6<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6>
        extends OverrideEntityQueryable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityFilterable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntitySelectable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityAggregatable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityGroupable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityHavingable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityJoinable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6>,
        EntityOrderable6<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6> {
}
