package com.easy.query.core.abstraction;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.jdbc.executor.EasyOldExecutor;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.merge.executor.internal.Executor;

/**
 * @FileName: DefaultJQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:47
 * @author xuejiaming
 */
public class DefaultEasyQueryRuntimeContext implements EasyQueryRuntimeContext {
    private final EasyQueryConfiguration easyQueryConfiguration;
    private final EntityMetadataManager entityMetadataManager;
    private final EasyQueryLambdaFactory easyQueryLambdaFactory;
    private final EasyConnectionManager easyConnectionManager;
    private final EasyOldExecutor easyExecutor;
    private final EntityExpressionExecutor entityExpressionExecutor;
    //    private final EasyQueryExecutor easyQueryExecutor;
    private final EasyJdbcTypeHandlerManager easyJdbcTypeHandler;
    private final EasySqlApiFactory easyQueryableFactory;
    private final EasyExpressionFactory easySqlExpressionFactory;
    private final TrackManager trackManager;
    private final EasyPageResultProvider easyPageResultProvider;
    private final EasyShardingOption easyShardingOption;
    private final EasyShardingExecutorService easyShardingExecutorService;

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          EasyQueryLambdaFactory easyQueryLambdaFactory,
                                          EasyConnectionManager easyConnectionManager,
                                          EasyOldExecutor easyExecutor,
                                          EntityExpressionExecutor entityExpressionExecutor,
                                          EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                          EasySqlApiFactory easyQueryableFactory,
                                          EasyExpressionFactory easySqlExpressionFactory,
                                          TrackManager trackManager,
                                          EasyPageResultProvider easyPageResultProvider,
                                          EasyShardingOption easyShardingOption,
                                          EasyShardingExecutorService easyShardingExecutorService){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.easyExecutor = easyExecutor;
        this.entityExpressionExecutor = entityExpressionExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
        this.easyQueryableFactory = easyQueryableFactory;
        this.easySqlExpressionFactory = easySqlExpressionFactory;
        this.trackManager = trackManager;
        this.easyPageResultProvider = easyPageResultProvider;
        this.easyShardingOption = easyShardingOption;
        this.easyShardingExecutorService = easyShardingExecutorService;
    }
    @Override
    public EasyQueryConfiguration getEasyQueryConfiguration() {
        return easyQueryConfiguration;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }

    @Override
    public EasyQueryLambdaFactory getEasyQueryLambdaFactory() {
        return easyQueryLambdaFactory;
    }

    @Override
    public EasyConnectionManager getConnectionManager() {
        return easyConnectionManager;
    }


    @Override
    public EasyOldExecutor getEasyExecutor() {
        return easyExecutor;
    }

    @Override
    public EntityExpressionExecutor getEntityExpressionExecutor() {
        return entityExpressionExecutor;
    }

    @Override
    public EasyJdbcTypeHandlerManager getEasyJdbcTypeHandlerManager() {
        return easyJdbcTypeHandler;
    }

    @Override
    public EasySqlApiFactory getSqlApiFactory() {
        return easyQueryableFactory;
    }

    @Override
    public EasyExpressionFactory getSqlExpressionFactory() {
        return easySqlExpressionFactory;
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
    public EasyShardingOption getEasyShardingOption() {
        return easyShardingOption;
    }

    @Override
    public EasyShardingExecutorService getEasyShardingExecutorService() {
        return easyShardingExecutorService;
    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
