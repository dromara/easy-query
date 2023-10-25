package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable.ProxyCountable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyTreeable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyUnionable1;
import com.easy.query.api.proxy.select.extension.queryable3.ClientProxyQueryable3Available;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyAggregatable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyFillable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyFilterable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyGroupable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyHavingable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyIncludeable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyJoinable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxyOrderable3;
import com.easy.query.api.proxy.select.extension.queryable3.ProxySelectable3;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2023/6/23 15:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends Query<T1>,
        Interceptable<ProxyQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>>,
        LogicDeletable<ProxyQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>>,
        TableReNameable<ProxyQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>>,
        QueryStrategy<ProxyQueryable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>>,
        ClientProxyQueryable3Available<T1,T2,T3>,
        ProxyUnionable1<T1Proxy,T1>,
        ProxyCountable1<T1Proxy, T1>,
        ProxyTreeable1<T1Proxy, T1>,
        ProxyIncludeable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyFilterable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyGroupable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyAggregatable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyHavingable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyOrderable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxySelectable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyJoinable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3>,
        ProxyFillable3<T1Proxy,T1,T2Proxy, T2,T3Proxy, T3> {



    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> cloneQueryable();


    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct() {
        return distinct(true);
    }

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> distinct(boolean condition);

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> limit(boolean condition, long offset, long rows);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asTracking();

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> asNoTracking();

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> useConnectionMode(ConnectionModeEnum connectionMode);
    ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> filterConfigure(ValueFilter valueFilter);
}