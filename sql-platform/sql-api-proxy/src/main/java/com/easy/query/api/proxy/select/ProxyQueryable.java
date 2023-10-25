package com.easy.query.api.proxy.select;

import com.easy.query.api.proxy.select.extension.queryable.ClientProxyQueryableAvailable;
import com.easy.query.api.proxy.select.extension.queryable.Proxy1Available;
import com.easy.query.api.proxy.select.extension.queryable.ProxyAggregatable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyCountable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyFillable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyFilterable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyForEachConfigurable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyGroupable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyHavingable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyIncludeable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyJoinable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyOrderable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxySelectable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyTreeable1;
import com.easy.query.api.proxy.select.extension.queryable.ProxyUnionable1;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.ProxySelector;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.List;

/**
 * create time 2023/6/21 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends Query<T1>,
        Interceptable<ProxyQueryable<T1Proxy, T1>>,
        LogicDeletable<ProxyQueryable<T1Proxy, T1>>,
        TableReNameable<ProxyQueryable<T1Proxy, T1>>,
        QueryStrategy<ProxyQueryable<T1Proxy, T1>>,
        ClientProxyQueryableAvailable<T1>,
        ProxyIncludeable1<T1Proxy, T1>,
        ProxyFilterable1<T1Proxy, T1>,
        ProxyGroupable1<T1Proxy, T1>,
        ProxyAggregatable1<T1Proxy, T1>,
        ProxyHavingable1<T1Proxy, T1>,
        ProxyOrderable1<T1Proxy, T1>,
        ProxyUnionable1<T1Proxy, T1>,
        ProxySelectable1<T1Proxy, T1>,
        ProxyJoinable1<T1Proxy, T1>,
        ProxyFillable1<T1Proxy, T1>,
        ProxyTreeable1<T1Proxy, T1>,
        ProxyCountable1<T1Proxy, T1>,
        Proxy1Available<T1Proxy,T1>,
        ProxyForEachConfigurable1<T1Proxy,T1> {

    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> List<TR> toList(TRProxy trProxy);

    @Override
    ProxyQueryable<T1Proxy, T1> cloneQueryable();


    /**
     * SELECT NOT EXISTS (
     * SELECT 1
     * FROM `table` AS `t`
     * WHERE (`t`.`columns` = ?))
     *
     * @param whereExpression 表达式最后一个是取反
     * @return
     */
    @Deprecated
    boolean all(SQLExpression2<ProxyFilter,T1Proxy> whereExpression);
    long countDistinct(SQLExpression2<ProxySelector, T1Proxy> selectExpression);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    ProxyQueryable<T1Proxy, T1> select(String columns);

    default ProxyQueryable<T1Proxy, T1> distinct() {
        return distinct(true);
    }

    ProxyQueryable<T1Proxy, T1> distinct(boolean condition);

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ProxyQueryable<T1Proxy, T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ProxyQueryable<T1Proxy, T1> limit(boolean condition, long offset, long rows);
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<T1Proxy, T1> include(SQLFuncExpression1<SQLKtNavigateIncludeImpl<T1>, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
//        return include(true, navigateIncludeSQLExpression);
//    }
//
//    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> ProxyQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<SQLKtNavigateIncludeImpl<T1>, ProxyQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
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
    ProxyQueryable<T1Proxy, T1> asTracking();

    ProxyQueryable<T1Proxy, T1> asNoTracking();

    ProxyQueryable<T1Proxy, T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    ProxyQueryable<T1Proxy, T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    ProxyQueryable<T1Proxy, T1> useConnectionMode(ConnectionModeEnum connectionMode);
    ProxyQueryable<T1Proxy, T1> filterConfigure(ValueFilter valueFilter);
}
