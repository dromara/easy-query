package org.jdqc.core.abstraction;

import org.jdqc.core.abstraction.metadata.EntityMetadata;
import org.jdqc.core.abstraction.metadata.EntityMetadataManager;
import org.jdqc.core.config.JDQCConfiguration;

/**
 * @FileName: JQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:46
 * @Created by xuejiaming
 */
public interface JDQCRuntimeContext {
    JDQCConfiguration getJDQCConfiguration();
    EntityMetadataManager getEntityMetadataManager();
}
