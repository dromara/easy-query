package com.easy.query.core.basic.api.select;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.ContextConfigure;
import com.easy.query.core.basic.api.internal.FilterConfigurable;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.TableLogicDeletable;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.extension.queryable.Aggregatable1;
import com.easy.query.core.basic.api.select.extension.queryable.Countable1;
import com.easy.query.core.basic.api.select.extension.queryable.Filterable1;
import com.easy.query.core.basic.api.select.extension.queryable.ForEachConfigurable1;
import com.easy.query.core.basic.api.select.extension.queryable.Groupable1;
import com.easy.query.core.basic.api.select.extension.queryable.Havingable1;
import com.easy.query.core.basic.api.select.extension.queryable.Includeable1;
import com.easy.query.core.basic.api.select.extension.queryable.Joinable1;
import com.easy.query.core.basic.api.select.extension.queryable.SubQueryConfigurable1;
import com.easy.query.core.basic.api.select.extension.queryable.SubQueryToGroupJoinable1;
import com.easy.query.core.basic.api.select.extension.queryable.Multiable1;
import com.easy.query.core.basic.api.select.extension.queryable.Orderable1;
import com.easy.query.core.basic.api.select.extension.queryable.Selectable1;
import com.easy.query.core.basic.api.select.extension.queryable.Treeable1;
import com.easy.query.core.basic.api.select.extension.queryable.Unionable1;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/6/1 17:26
 * 属性可查询链式接口
 *
 * @author xuejiaming
 */
public interface ClientQueryable<T1> extends Query<T1>,
        Interceptable<ClientQueryable<T1>>,
        LogicDeletable<ClientQueryable<T1>>,
        TableReNameable<ClientQueryable<T1>>,
        TableLogicDeletable<ClientQueryable<T1>>,
        FilterConfigurable<ClientQueryable<T1>>,
        ContextConfigure<ClientQueryable<T1>>,
        Aggregatable1<T1>,
        Joinable1<T1>,
        SubQueryToGroupJoinable1<T1>,
        SubQueryConfigurable1<T1>,
        Multiable1<T1>,
        Filterable1<T1>,
        Selectable1<T1>,
        Groupable1<T1>,
        Havingable1<T1>,
        Orderable1<T1>,
        Unionable1<T1>,
        Includeable1<T1>,
        Countable1<T1>,
        ForEachConfigurable1<T1>,
        Treeable1<T1> {
    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @NotNull
    @Override
    ClientQueryable<T1> cloneQueryable();
    @NotNull
    @Override
   default ClientQueryable<T1> toCteAs(){
        return toCteAs(getRuntimeContext().getCteTableNamedProvider().getDefaultCteTableName(queryClass()));
    }

    @NotNull
    @Override
    ClientQueryable<T1> toCteAs(String tableName);

    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @NotNull
    @Override
    ClientQueryable<T1> select(@NotNull String columns);

    @NotNull
    default ClientQueryable<T1> distinct() {
        return distinct(true);
    }


    @NotNull ClientQueryable<T1> distinct(boolean condition);


    @NotNull
    @Override
    default ClientQueryable<T1> limit(long rows) {
        return limit(true, rows);
    }


    @NotNull
    @Override
    default ClientQueryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }


    @NotNull
    @Override
    default ClientQueryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }


    @NotNull
    @Override
    ClientQueryable<T1> limit(boolean condition, long offset, long rows);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @NotNull ClientQueryable<T1> asTracking();

    @NotNull ClientQueryable<T1> asNoTracking();

    @NotNull ClientQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @NotNull ClientQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @NotNull ClientQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
