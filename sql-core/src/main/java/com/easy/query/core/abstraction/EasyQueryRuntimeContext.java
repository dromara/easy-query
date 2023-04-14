package com.easy.query.core.abstraction;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.expression.executor.query.EasyQueryExecutor;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.merge.executor.internal.Executor;

/**
 * @FileName: JQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:46
 * @author xuejiaming
 */
public interface EasyQueryRuntimeContext {
    EasyQueryConfiguration getEasyQueryConfiguration();
    EntityMetadataManager getEntityMetadataManager();
    EasyQueryLambdaFactory getEasyQueryLambdaFactory();
    EasyConnectionManager getConnectionManager();
    EasyExecutor getEasyExecutor();
    EasyQueryExecutor getEasyQueryExecutor();
    EasyJdbcTypeHandlerManager getEasyJdbcTypeHandlerManager();
    EasySqlApiFactory getSqlApiFactory();
    EasyExpressionFactory getSqlExpressionFactory();
    TrackManager getTrackManager();
    EasyPageResultProvider getEasyPageResultProvider();
    EasyShardingOption getEasyShardingOption();
    EasyShardingExecutorService getEasyShardingExecutorService();
    Executor getExecutor();
}
