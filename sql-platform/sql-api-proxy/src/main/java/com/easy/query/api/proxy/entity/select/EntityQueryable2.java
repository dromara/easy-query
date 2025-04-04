package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityAggregatable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityFilterable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityGroupable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityJoinable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntitySubQueryToGroupJoinable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntityOrderable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.EntitySelectable2;
import com.easy.query.api.proxy.entity.select.extension.queryable2.override.OverrideEntityQueryable2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends OverrideEntityQueryable2<T1Proxy,T1,T2Proxy, T2>,
        EntityFilterable2<T1Proxy,T1,T2Proxy, T2>,
        EntitySelectable2<T1Proxy,T1,T2Proxy, T2>,
        EntityAggregatable2<T1Proxy,T1,T2Proxy, T2>,
        EntityGroupable2<T1Proxy,T1,T2Proxy, T2>,
//        EntityHavingable2<T1Proxy,T1,T2Proxy, T2>,
        EntityJoinable2<T1Proxy,T1,T2Proxy, T2>,
        EntitySubQueryToGroupJoinable2<T1Proxy,T1,T2Proxy, T2>,
        EntityOrderable2<T1Proxy,T1,T2Proxy, T2> {

}
