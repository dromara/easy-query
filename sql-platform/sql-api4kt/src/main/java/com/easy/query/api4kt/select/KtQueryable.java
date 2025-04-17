package com.easy.query.api4kt.select;

import com.easy.query.api4kt.select.extension.queryable.ClientKtQueryableAvailable;
import com.easy.query.api4kt.select.extension.queryable.SQLKtAggregatable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtCountable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtFilterable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtForEachConfigurable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtGroupable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtHavingable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtIncludeable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtJoinable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtOrderable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtSelectable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtTreeable1;
import com.easy.query.api4kt.select.extension.queryable.SQLKtUnionable1;
import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.internal.ContextConfigure;
import com.easy.query.core.basic.api.internal.FilterConfigurable;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.LogicDeletable;
import com.easy.query.core.basic.api.internal.QueryStrategy;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * @author xuejiaming
 * @FileName: Select0.java
 * @Description: 文件说明
 * @Date: 2023/2/6 21:28
 */
public interface KtQueryable<T1> extends Query<T1>,
        Interceptable<KtQueryable<T1>>,
        LogicDeletable<KtQueryable<T1>>,
        TableReNameable<KtQueryable<T1>>,
        QueryStrategy<KtQueryable<T1>>,
        ContextConfigure<KtQueryable<T1>>,
        FilterConfigurable<KtQueryable<T1>>,
        SQLKtAggregatable1<T1>,
        SQLKtGroupable1<T1>,
        SQLKtHavingable1<T1>,
        SQLKtJoinable1<T1>,
        SQLKtOrderable1<T1>,
        SQLKtSelectable1<T1>,
        SQLKtUnionable1<T1>,
        SQLKtFilterable1<T1>,
        SQLKtIncludeable1<T1>,
        SQLKtCountable1<T1>,
        ClientKtQueryableAvailable<T1>,
        SQLKtForEachConfigurable1<T1>,
        SQLKtTreeable1<T1> {

    /**
     * 只clone表达式共享上下文
     * 如果是两个独立的表达式建议重新创建如果是
     *
     * @return
     */
    @Override
    KtQueryable<T1> cloneQueryable();
    /**
     * 设置column所有join表都会生效
     *
     * @param columns
     * @return
     */
    @Override
    KtQueryable<T1> select(String columns);

    default KtQueryable<T1> distinct() {
        return distinct(true);
    }

    KtQueryable<T1> distinct(boolean condition);

    @Override
    default KtQueryable<T1> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default KtQueryable<T1> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default KtQueryable<T1> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    KtQueryable<T1> limit(boolean condition, long offset, long rows);

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    KtQueryable<T1> asTracking();

    KtQueryable<T1> asNoTracking();

    KtQueryable<T1> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    KtQueryable<T1> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    KtQueryable<T1> useConnectionMode(ConnectionModeEnum connectionMode);

//    SQLExpressionProvider<T1> getSQLExpressionProvider1();

}
