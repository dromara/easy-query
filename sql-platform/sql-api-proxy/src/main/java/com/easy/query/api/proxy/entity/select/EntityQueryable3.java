package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityAggregatable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityFilterable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityGroupable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityHavingable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityJoinable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntityOrderable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.EntitySelectable3;
import com.easy.query.api.proxy.entity.select.extension.queryable3.override.OverrideEntityQueryable3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3>
        extends OverrideEntityQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityFilterable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntitySelectable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityAggregatable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityGroupable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityHavingable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityJoinable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        EntityOrderable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3> {

}
