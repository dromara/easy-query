package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.basic.EasyConnectionManager;
import org.easy.query.core.config.EasyQueryConfiguration;

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
    private final EasyJdbcTypeHandler easyJdbcTypeHandler;

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                          EntityMetadataManager entityMetadataManager,
                                          EasyQueryLambdaFactory easyQueryLambdaFactory,
                                          EasyConnectionManager easyConnectionManager,
                                          EasyExecutor easyExecutor,
                                          EasyJdbcTypeHandler easyJdbcTypeHandler){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnectionManager = easyConnectionManager;
        this.easyExecutor = easyExecutor;
        this.easyJdbcTypeHandler = easyJdbcTypeHandler;
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
    public EasyJdbcTypeHandler getEasyJdbcTypeHandler() {
        return easyJdbcTypeHandler;
    }
}
