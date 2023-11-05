package com.easy.query.core.basic.extension.logicdel;


import com.easy.query.core.metadata.LogicDeleteMetadata;

import java.util.Set;

/**
 * @FileName: EasyLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 07:59
 * @author xuejiaming
 */
public interface LogicDeleteStrategy {
    /**
     * 逻辑删除策略名称
     * @return
     */
    String getStrategy();

    /**
     * 哪些属性类型支持
     * @return
     */
    Set<Class<?>> allowedPropertyTypes();

    /**
     * 配置逻辑删除过滤和set
     * @param builder
     */
    LogicDeleteMetadata configureBuild(LogicDeleteBuilder builder);
}
