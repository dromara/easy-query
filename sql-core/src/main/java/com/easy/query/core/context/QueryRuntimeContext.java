package com.easy.query.core.context;

import com.easy.query.core.api.SQLApiFactory;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.func.ColumnFunctionFactory;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.route.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.route.manager.TableRouteManager;

/**
 * @FileName: JQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:46
 * @author xuejiaming
 */
public interface QueryRuntimeContext {
    <T> T getService(Class<T> serviceType);
    EasyQueryDataSource getEasyQueryDataSource();
    QueryConfiguration getQueryConfiguration();
    EntityMetadataManager getEntityMetadataManager();
    SQLExpressionInvokeFactory getSQLExpressionInvokeFactory();
    ConnectionManager getConnectionManager();
    EntityExpressionExecutor getEntityExpressionExecutor();
    JdbcTypeHandlerManager getJdbcTypeHandlerManager();
    SQLApiFactory getSQLApiFactory();
    ExpressionBuilderFactory getExpressionBuilderFactory();
    ExpressionFactory getExpressionFactory();
    TrackManager getTrackManager();
    EasyPageResultProvider getEasyPageResultProvider();
    ShardingExecutorService getShardingExecutorService();
    TableRouteManager getTableRouteManager();
    DataSourceRouteManager getDataSourceRouteManager();
    ShardingComparer getShardingComparer();
    ShardingQueryCountManager getShardingQueryCountManager();
    ColumnFunctionFactory getColumnFunctionFactory();
    DataSourceUnitFactory getDataSourceUnitFactory();
}
