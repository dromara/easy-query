package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable.ProxyCountable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyUnionable1;
import com.easy.query.api.proxy.select.extension.queryable4.ClientProxyQueryable4Available;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyAggregatable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyFillable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyFilterable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyGroupable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyHavingable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyIncludeable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyJoinable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxyOrderable4;
import com.easy.query.api.proxy.select.extension.queryable4.ProxySelectable4;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ConditionAccepter;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4>  extends Query<T1>,
        Interceptable<ProxyQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>>,
        LogicDeletable<ProxyQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>>,
        TableReNameable<ProxyQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>>,
        QueryStrategy<ProxyQueryable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>>,
        ClientProxyQueryable4Available<T1,T2,T3,T4>,
        ProxyUnionable1<T1Proxy,T1>,
        ProxyCountable1<T1Proxy, T1>,
        ProxyIncludeable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyFilterable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyGroupable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyAggregatable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyHavingable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyOrderable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxySelectable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyJoinable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4>,
        ProxyFillable4<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4> {




    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> cloneQueryable();


    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct() {
        return distinct(true);
    }

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> distinct(boolean condition);

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> limit(boolean condition, long offset, long rows);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asTracking();

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> asNoTracking();

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> useConnectionMode(ConnectionModeEnum connectionMode);
    ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> conditionConfigure(ConditionAccepter conditionAccepter);
}
