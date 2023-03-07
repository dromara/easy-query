package org.easy.query.core.configuration.global;


import org.easy.query.core.abstraction.metadata.EntityMetadata;

import java.util.Set;

/**
 * @FileName: GlobalLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 07:59
 * @Created by xuejiaming
 */
public interface GlobalLogicDeleteStrategy {
    String getStrategy();
    Set<Class<?>> expectPropertyTypes();
    void configure(EntityMetadata entityMetadata, String propertyName, Class<?> propertyType);
}
