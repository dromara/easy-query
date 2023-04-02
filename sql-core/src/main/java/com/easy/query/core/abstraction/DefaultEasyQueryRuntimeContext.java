package com.easy.query.core.abstraction;

import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.plugin.track.TrackManager;

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
    private final EasyExecutor easyExecutor;
    private final EasyJdbcTypeHandlerManager easyJdbcTypeHandler;
    private final EasySqlApiFactory easyQueryableFactory;
    private final EasyExpressionFactory easySqlExpressionFactory;
    private final TrackManager trackManager;
    private final EasyPageResultProvider easyPageResultProvider;

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          EasyQueryLambdaFactory easyQueryLambdaFactory,
                                          EasyConnectionManager easyConnectionManager,
                                          EasyExecutor easyExecutor,
                                          EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                          EasySqlApiFactory easyQueryableFactory,
                                          EasyExpressionFactory easySqlExpressionFactory,
                                          TrackManager trackManager,
                                          EasyPageResultProvider easyPageResultProvider){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.easyExecutor = easyExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
        this.easyQueryableFactory = easyQueryableFactory;
        this.easySqlExpressionFactory = easySqlExpressionFactory;
        this.trackManager = trackManager;
        this.easyPageResultProvider = easyPageResultProvider;
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
    public EasyExecutor getEasyExecutor() {
        return easyExecutor;
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
}
