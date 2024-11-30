package com.easy.query.core.basic.api.internal;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;

/**
 * create time 2024/11/30 11:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExpressionConfigurable<TChain> extends Interceptable<TChain>,
        FilterConfigurable<TChain>,
        LogicDeletable<TChain>,
        TableLogicDeletable<TChain>, TableReNameable<TChain>,
        ContextConfigure<TChain> {
    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     * 如果当前启用了追踪查询并且在当前上下文已经追加了当前trackKey的对象那么当前查询结果的对象不会被返回,返回的是被追踪的当前对象,
     * 如果对象A:{id:1,name:2}已经被追踪了,新查询的结果是对象A:{id:1,name:3},那么查询到的数据是{id:1,name:3}但是用户获取到的数据是{id:1,name:2}
     * 所以尽可能在追踪后调用entity update,而不是重复查询对应对象
     *
     * @return
     */
    TChain asTracking();

    TChain asNoTracking();

    TChain useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    TChain useMaxShardingQueryLimit(int maxShardingQueryLimit);

    TChain useConnectionMode(ConnectionModeEnum connectionMode);

    TChain filterConfigure(ValueFilter valueFilter);
}
