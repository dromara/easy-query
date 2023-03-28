package com.easy.query.core.basic.plugin.logicdel;


import com.easy.query.core.metadata.EntityMetadata;

import java.util.Set;

/**
 * @FileName: EasyLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 07:59
 * @author xuejiaming
 */
public interface EasyLogicDeleteStrategy {
    String getStrategy();
    Set<Class<?>> allowedPropertyTypes();
    void configure(LogicDeleteBuilder builder);
}
