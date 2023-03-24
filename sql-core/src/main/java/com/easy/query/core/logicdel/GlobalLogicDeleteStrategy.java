package com.easy.query.core.logicdel;


import com.easy.query.core.abstraction.metadata.EntityMetadata;

import java.util.Set;

/**
 * @FileName: GlobalLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 07:59
 * @author xuejiaming
 */
public interface GlobalLogicDeleteStrategy {
    String getStrategy();
    Set<Class<?>> expectPropertyTypes();
    void configure(EntityMetadata entityMetadata, String propertyName, Class<?> propertyType);
}
