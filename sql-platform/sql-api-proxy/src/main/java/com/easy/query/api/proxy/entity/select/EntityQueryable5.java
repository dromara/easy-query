package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityAggregatable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityFilterable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityGroupable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityJoinable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntityOrderable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.EntitySelectable5;
import com.easy.query.api.proxy.entity.select.extension.queryable5.override.OverrideEntityQueryable5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable5<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5>
        extends OverrideEntityQueryable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntityFilterable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntitySelectable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntityAggregatable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntityGroupable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
//        EntityHavingable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntityJoinable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5>,
        EntityOrderable5<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5> {

}
