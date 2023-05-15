package com.easy.query.core.basic.jdbc.executor.internal.unit;

import com.easy.query.core.basic.jdbc.executor.internal.common.DataSourceSqlExecutorUnit;
import com.easy.query.core.basic.jdbc.executor.internal.sharding.merger.ShardingMerger;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/4/13 21:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Executor<TResult> {
    ShardingMerger<TResult> getShardingMerger();
    List<TResult> execute(DataSourceSqlExecutorUnit dataSourceSqlExecutorUnit) throws SQLException;
}
