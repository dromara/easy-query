package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.config.EasyConnector;
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
    private final EasyConnector easyConnector;
    private final EasyExecutor easyExecutor;

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration, EntityMetadataManager entityMetadataManager,EasyQueryLambdaFactory easyQueryLambdaFactory,EasyConnector easyConnector,EasyExecutor easyExecutor){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
        this.easyQueryLambdaFactory = easyQueryLambdaFactory;
        this.easyConnector = easyConnector;
        this.easyExecutor = easyExecutor;
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
    public EasyConnector getEasyConnector() {
        return easyConnector;
    }

    @Override
    public EasyExecutor getEasyExecutor() {
        return easyExecutor;
    }
}
