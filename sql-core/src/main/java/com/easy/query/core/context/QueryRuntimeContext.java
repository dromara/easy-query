package com.easy.query.core.context;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.column2mapkey.Column2MapKeyConversion;
import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.expression.func.ColumnFunctionFactory;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.job.EasyTimeJobManager;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;

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

    //    SQLApiFactory getSQLApiFactory();
    SQLClientApiFactory getSQLClientApiFactory();

    ExpressionBuilderFactory getExpressionBuilderFactory();
    ExpressionFactory getExpressionFactory();
    TrackManager getTrackManager();
    EasyPageResultProvider getEasyPageResultProvider();
    ShardingExecutorService getShardingExecutorService();
    TableRouteManager getTableRouteManager();
    DataSourceRouteManager getDataSourceRouteManager();
    ShardingComparer getShardingComparer();
    ShardingQueryCountManager getShardingQueryCountManager();
    @Deprecated
    ColumnFunctionFactory getColumnFunctionFactory();
    DataSourceUnitFactory getDataSourceUnitFactory();

    SQLSegmentFactory getSQLSegmentFactory();

    DataSourceManager getDataSourceManager();
    EasyTimeJobManager getEasyTimeJobManager();
    IncludeProcessorFactory getIncludeProcessorFactory();
    IncludeParserEngine getIncludeParserEngine();
    WhereObjectQueryExecutor getWhereObjectQueryExecutor();
    ObjectSortQueryExecutor getObjectSortQueryExecutor();
    SQLFunc fx();
    JdbcExecutorListener getJdbcExecutorListener();
    AssertExceptionFactory getAssertExceptionFactory();
    SQLParameterPrintFormat getSQLParameterPrintFormat();
    Column2MapKeyConversion getColumn2MapKeyConversion();
}
