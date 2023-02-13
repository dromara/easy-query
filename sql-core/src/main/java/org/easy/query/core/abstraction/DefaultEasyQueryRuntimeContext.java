package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
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

    public DefaultEasyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration, EntityMetadataManager entityMetadataManager){
        this.easyQueryConfiguration = easyQueryConfiguration;
        this.entityMetadataManager = entityMetadataManager;
    }
    @Override
    public EasyQueryConfiguration getEasyQueryConfiguration() {
        return easyQueryConfiguration;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }
}
