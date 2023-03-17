package com.easy.query.core.abstraction;

import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;

/**
 * @FileName: DefaultJQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:47
 * @Created by xuejiaming
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

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          EasyQueryLambdaFactory easyQueryLambdaFactory,
                                          EasyConnectionManager easyConnectionManager,
                                          EasyExecutor easyExecutor,
                                          EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                          EasySqlApiFactory easyQueryableFactory,
                                          EasyExpressionFactory easySqlExpressionFactory){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.easyExecutor = easyExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
        this.easyQueryableFactory = easyQueryableFactory;
        this.easySqlExpressionFactory = easySqlExpressionFactory;
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
}