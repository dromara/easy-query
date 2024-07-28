package com.easy.query.api.proxy.entity.select;

import com.easy.query.api.proxy.entity.select.extension.EntityAvailable;
import com.easy.query.api.proxy.entity.select.extension.FlatListResultAble;
import com.easy.query.api.proxy.entity.select.extension.queryable.ClientEntityQueryableAvailable;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityAggregatable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityCountable1;
//import com.easy.query.api.proxy.entity.select.extension.queryable.EntityFillable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityFilterable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityGroupable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityHavingable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityIncludeable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityIncludesable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityJoinable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityOrderable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntitySelectable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityTreeable1;
import com.easy.query.api.proxy.entity.select.extension.queryable.EntityUnionable1;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.ContextConfigure;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableLogicDeletable;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.ClientQueryableAvailable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/12/4 09:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientQueryableAvailable<T1>,
        FlatListResultAble<T1Proxy,T1>,
        Interceptable<EntityQueryable<T1Proxy, T1>>,
        LogicDeletable<EntityQueryable<T1Proxy, T1>>,
        TableReNameable<EntityQueryable<T1Proxy, T1>>,
        TableLogicDeletable<EntityQueryable<T1Proxy, T1>>,
        QueryStrategy<EntityQueryable<T1Proxy, T1>>,
        ContextConfigure<EntityQueryable<T1Proxy, T1>>,
        ClientEntityQueryableAvailable<T1>,
        EntityFilterable1<T1Proxy,T1>,
        EntityCountable1<T1Proxy,T1>,
        EntityHavingable1<T1Proxy,T1>,
        EntityIncludeable1<T1Proxy,T1>,
        EntityIncludesable1<T1Proxy,T1>,
        EntityAggregatable1<T1Proxy,T1>,
        EntityOrderable1<T1Proxy,T1>,
        EntitySelectable1<T1Proxy,T1>,
        EntityUnionable1<T1Proxy,T1>,
        EntityGroupable1<T1Proxy,T1>,
        EntityJoinable1<T1Proxy,T1>,
//        EntityFillable1<T1Proxy,T1>,
//        EntityMultiable1<T1Proxy,T1>,
        EntityTreeable1<T1Proxy,T1>,
        EntityAvailable<T1Proxy,T1> {



    ClientQueryable<T1> getClientQueryable();
//
//    @Deprecated
//    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    EntityQueryable<T1Proxy, T1> cloneQueryable();

    long countDistinct(SQLFuncExpression1<T1Proxy, SQLColumn<?,?>> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    EntityQueryable<T1Proxy, T1> select(String columns);

    default EntityQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    EntityQueryable<T1Proxy, T1> distinct(boolean condition);

    @Override
    default EntityQueryable<T1Proxy, T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default EntityQueryable<T1Proxy, T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default EntityQueryable<T1Proxy, T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    EntityQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows);
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<SQLKtNavigateIncludeImpl<T1>, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        return include(true, navigateIncludeSQLExpression);
//    }
//
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<SQLKtNavigateIncludeImpl<T1>, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        if (condition) {
//            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new SQLKtNavigateIncludeImpl<>(navigateInclude)).getClientQueryable());
//        }
//        return this;
//    }

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    EntityQueryable<T1Proxy, T1> asTracking();

    EntityQueryable<T1Proxy, T1> asNoTracking();

    EntityQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    EntityQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    EntityQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode);
    EntityQueryable<T1Proxy, T1> filterConfigure(ValueFilter valueFilter);
}
