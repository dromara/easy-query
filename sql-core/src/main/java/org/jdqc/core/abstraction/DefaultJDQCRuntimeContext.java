package org.jdqc.core.abstraction;

import org.jdqc.core.abstraction.metadata.EntityMetadataManager;
import org.jdqc.core.config.JDQCConfiguration;

/**
 * @FileName: DefaultJQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:47
 * @Created by xuejiaming
 */
public class DefaultJDQCRuntimeContext implements JDQCRuntimeContext {
    private final JDQCConfiguration jdqcConfiguration;
    private final EntityMetadataManager entityMetadataManager;

    public DefaultJDQCRuntimeContext(JDQCConfiguration jdqcConfiguration, EntityMetadataManager entityMetadataManager){
        this.jdqcConfiguration = jdqcConfiguration;
        this.entityMetadataManager = entityMetadataManager;
    }
    @Override
    public JDQCConfiguration getJDQCConfiguration() {
        return jdqcConfiguration;
    }

    @Override
    public EntityMetadataManager getEntityMetadataManager() {
        return entityMetadataManager;
    }
}
