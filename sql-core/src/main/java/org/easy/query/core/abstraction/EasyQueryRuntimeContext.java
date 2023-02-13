package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.metadata.EntityMetadataManager;
import org.easy.query.core.config.EasyQueryConfiguration;

/**
 * @FileName: JQDCRuntimeContext.java
 * @Description: 文件说明
 * @Date: 2023/2/11 13:46
 * @Created by xuejiaming
 */
public interface EasyQueryRuntimeContext {
    EasyQueryConfiguration getEasyQueryConfiguration();
    EntityMetadataManager getEntityMetadataManager();
}
