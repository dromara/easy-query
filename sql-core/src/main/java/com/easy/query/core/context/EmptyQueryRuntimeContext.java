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
 * create time 2024/6/8 21:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyQueryRuntimeContext implements QueryRuntimeContext{
    public static final QueryRuntimeContext DEFAULT=new EmptyQueryRuntimeContext();
    @Override
    public <T> T getService(Class<T> serviceType) {
        return null;
    }

    @Override
    public EasyQueryDataSource getEasyQueryDataSource() {
        return null;
    }

    @Override
    public QueryConfiguration getQueryConfiguration() {
        return null;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return null;
    }

    @Override
    public SQLExpressionInvokeFactory getSQLExpressionInvokeFactory() {
        return null;
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return null;
    }

    @Override
    public EntityExpressionExecutor getEntityExpressionExecutor() {
        return null;
    }

    @Override
    public JdbcTypeHandlerManager getJdbcTypeHandlerManager() {
        return null;
    }

    @Override
    public SQLClientApiFactory getSQLClientApiFactory() {
        return null;
    }

    @Override
    public ExpressionBuilderFactory getExpressionBuilderFactory() {
        return null;
    }

    @Override
    public ExpressionFactory getExpressionFactory() {
        return null;
    }

    @Override
    public TrackManager getTrackManager() {
        return null;
    }

    @Override
    public EasyPageResultProvider getEasyPageResultProvider() {
        return null;
    }

    @Override
    public ShardingExecutorService getShardingExecutorService() {
        return null;
    }

    @Override
    public TableRouteManager getTableRouteManager() {
        return null;
    }

    @Override
    public DataSourceRouteManager getDataSourceRouteManager() {
        return null;
    }

    @Override
    public ShardingComparer getShardingComparer() {
        return null;
    }

    @Override
    public ShardingQueryCountManager getShardingQueryCountManager() {
        return null;
    }

    @Override
    public ColumnFunctionFactory getColumnFunctionFactory() {
        return null;
    }

    @Override
    public DataSourceUnitFactory getDataSourceUnitFactory() {
        return null;
    }

    @Override
    public SQLSegmentFactory getSQLSegmentFactory() {
        return null;
    }

    @Override
    public DataSourceManager getDataSourceManager() {
        return null;
    }

    @Override
    public EasyTimeJobManager getEasyTimeJobManager() {
        return null;
    }

    @Override
    public IncludeProcessorFactory getIncludeProcessorFactory() {
        return null;
    }

    @Override
    public IncludeParserEngine getIncludeParserEngine() {
        return null;
    }

    @Override
    public WhereObjectQueryExecutor getWhereObjectQueryExecutor() {
        return null;
    }

    @Override
    public ObjectSortQueryExecutor getObjectSortQueryExecutor() {
        return null;
    }

    @Override
    public SQLFunc fx() {
        return null;
    }

    @Override
    public JdbcExecutorListener getJdbcExecutorListener() {
        return null;
    }

    @Override
    public AssertExceptionFactory getAssertExceptionFactory() {
        return null;
    }

    @Override
    public SQLParameterPrintFormat getSQLParameterPrintFormat() {
        return null;
    }

    @Override
    public Column2MapKeyConversion getColumn2MapKeyConversion() {
        return null;
    }
}
