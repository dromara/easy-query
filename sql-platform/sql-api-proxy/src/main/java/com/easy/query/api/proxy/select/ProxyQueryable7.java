package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable.ProxyCountable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyUnionable1;
import com.easy.query.api.proxy.select.extension.queryable7.ClientProxyQueryable7Available;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyAggregatable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyFillable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyFilterable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyGroupable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyHavingable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyIncludeable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyJoinable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxyOrderable7;
import com.easy.query.api.proxy.select.extension.queryable7.ProxySelectable7;
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
public interface ProxyQueryable7<T1Proxy extends ProxyEntity<T1Proxy, T1>,
        T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends Query<T1>,
        Interceptable<ProxyQueryable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>>,
        LogicDeletable<ProxyQueryable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>>,
        TableReNameable<ProxyQueryable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>>,
        QueryStrategy<ProxyQueryable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>>,
        ClientProxyQueryable7Available<T1,T2,T3,T4,T5,T6,T7>,
        ProxyUnionable1<T1Proxy,T1>,
        ProxyCountable1<T1Proxy, T1>,
        ProxyIncludeable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyFilterable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyGroupable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyAggregatable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyHavingable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyOrderable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxySelectable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyJoinable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7>,
        ProxyFillable7<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3,T4Proxy, T4,T5Proxy, T5,T6Proxy, T6,T7Proxy, T7> {




    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> cloneQueryable();


    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> distinct() {
        return distinct(true);
    }

    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> distinct(boolean condition);

    @Override
    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> limit(boolean condition, long offset, long rows);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asTracking();

    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> asNoTracking();

    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> useConnectionMode(ConnectionModeEnum connectionMode);
    ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> conditionConfigure(ConditionAccepter conditionAccepter);
}
