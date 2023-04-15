package com.easy.query.core.sharding.merge.executor.internal;

import com.easy.query.core.sharding.merge.executor.common.DataSourceSqlExecutorUnit;

import java.util.List;

/**
 * create time 2023/4/13 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Executor<TResult> {
    ShardingMerger<TResult> getShardingMerger();
    List<TResult> execute(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit);
}
