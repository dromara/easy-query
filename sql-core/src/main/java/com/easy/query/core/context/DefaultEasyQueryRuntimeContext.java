package com.easy.query.core.context;

import com.easy.query.core.api.SQLApiFactory;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.ShardingExecutorService;
import com.easy.query.core.expression.parser.factory.SQLExpressionInvokeFactory;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.func.ColumnFunctionFactory;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.con.ConnectionManager;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.comparer.ShardingComparer;
import com.easy.query.core.sharding.limit.MultiConnectionLimit;
import com.easy.query.core.sharding.manager.ShardingQueryCountManager;
import com.easy.query.core.sharding.route.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.route.manager.TableRouteManager;

/**
 * @author xuejiaming
 * @FileName: DefaultJQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:47
 */
public class DefaultEasyQueryRuntimeContext implements QueryRuntimeContext {
    private final ServiceProvider serviceProvider;
    private final EasyQueryDataSource easyQueryDataSource;
    private final QueryConfiguration easyQueryConfiguration;
    private final EntityMetadataManager entityMetadataManager;
    private final SQLExpressionInvokeFactory easyQueryLambdaFactory;
    private final ConnectionManager easyConnectionManager;
    private final EntityExpressionExecutor entityExpressionExecutor;
    //    private final EasyQueryExecutor easyQueryExecutor;
    private final JdbcTypeHandlerManager easyJdbcTypeHandler;
    private final SQLApiFactory easyQueryableFactory;
    private final ExpressionBuilderFactory expressionBuilderFactory;
    private final TrackManager trackManager;
    private final EasyPageResultProvider easyPageResultProvider;
    private final ShardingExecutorService easyShardingExecutorService;
    private final ExpressionFactory easyExpressionFactory;
    private final TableRouteManager tableRouteManager;
    private final DataSourceRouteManager dataSourceRouteManager;
    private final ShardingComparer shardingComparer;
    private final ShardingQueryCountManager shardingQueryCountManager;
    private final ColumnFunctionFactory columnFunctionFactory;
    private final MultiConnectionLimit multiConnectionLimit;

    public DefaultEasyQueryRuntimeContext(ServiceProvider serviceProvider,
                                          EasyQueryDataSource easyQueryDataSource,
                                          QueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          SQLExpressionInvokeFactory easyQueryLambdaFactory,
                                          ConnectionManager easyConnectionManager,
                                          EntityExpressionExecutor entityExpressionExecutor,
                                          JdbcTypeHandlerManager easyJdbcTypeHandler,
                                          SQLApiFactory easyQueryableFactory,
                                          ExpressionBuilderFactory expressionBuilderFactory,
                                          TrackManager trackManager,
                                          EasyPageResultProvider easyPageResultProvider,
                                          ShardingExecutorService easyShardingExecutorService,
                                          ExpressionFactory easyExpressionFactory,
                                          TableRouteManager tableRouteManager,
                                          DataSourceRouteManager dataSourceRouteManager,
                                          ShardingComparer shardingComparer,
                                          ShardingQueryCountManager shardingQueryCountManager,
                                          ColumnFunctionFactory columnFunctionFactory,
                                          MultiConnectionLimit multiConnectionLimit) {
        this.serviceProvider = serviceProvider;
        this.easyQueryDataSource = easyQueryDataSource;
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.entityExpressionExecutor = entityExpressionExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
        this.easyQueryableFactory = easyQueryableFactory;
        this.expressionBuilderFactory = expressionBuilderFactory;
        this.trackManager = trackManager;
        this.easyPageResultProvider = easyPageResultProvider;
        this.easyShardingExecutorService = easyShardingExecutorService;
        this.easyExpressionFactory = easyExpressionFactory;
        this.tableRouteManager = tableRouteManager;
        this.dataSourceRouteManager = dataSourceRouteManager;
        this.shardingComparer = shardingComparer;
        this.shardingQueryCountManager = shardingQueryCountManager;
        this.columnFunctionFactory = columnFunctionFactory;
        this.multiConnectionLimit = multiConnectionLimit;
    }

    @Override
    public <T> T getService(Class<T> serviceType) {
        return serviceProvider.getService(serviceType);
    }

    @Override
    public EasyQueryDataSource getEasyQueryDataSource() {
        return easyQueryDataSource;
    }

    @Override
    public QueryConfiguration getQueryConfiguration() {
        return easyQueryConfiguration;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }

    @Override
    public SQLExpressionInvokeFactory getSQLExpressionInvokeFactory() {
        return easyQueryLambdaFactory;
    }

    @Override
    public ConnectionManager getConnectionManager() {
        return easyConnectionManager;
    }

    @Override
    public EntityExpressionExecutor getEntityExpressionExecutor() {
        return entityExpressionExecutor;
    }

    @Override
    public JdbcTypeHandlerManager getJdbcTypeHandlerManager() {
        return easyJdbcTypeHandler;
    }

    @Override
    public SQLApiFactory getSQLApiFactory() {
        return easyQueryableFactory;
    }

    @Override
    public ExpressionBuilderFactory getExpressionBuilderFactory() {
        return expressionBuilderFactory;
    }

    @Override
    public ExpressionFactory getExpressionFactory() {
        return easyExpressionFactory;
    }

    @Override
    public TrackManager getTrackManager() {
        return trackManager;
    }

    @Override
    public EasyPageResultProvider getEasyPageResultProvider() {
        return easyPageResultProvider;
    }

    @Override
    public ShardingExecutorService getShardingExecutorService() {
        return easyShardingExecutorService;
    }

    @Override
    public TableRouteManager getTableRouteManager() {
        return tableRouteManager;
    }

    @Override
    public DataSourceRouteManager getDataSourceRouteManager() {
        return dataSourceRouteManager;
    }

    @Override
    public ShardingComparer getShardingComparer() {
        return shardingComparer;
    }

    @Override
    public ShardingQueryCountManager getShardingQueryCountManager() {
        return shardingQueryCountManager;
    }

    @Override
    public ColumnFunctionFactory getColumnFunctionFactory() {
        return columnFunctionFactory;
    }

    @Override
    public MultiConnectionLimit getMultiConnectionLimit() {
        return multiConnectionLimit;
    }
}
