package com.easy.query.core.basic.extension.logicdel;


import com.easy.query.core.metadata.LogicDeleteMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: EasyLogicDeleteStrategy.java
 * @Description: 文件说明
 * create time 2023/3/7 07:59
 */
public interface LogicDeleteStrategy {
    /**
     * 逻辑删除策略名称
     *
     * @return
     */
    String getStrategy();

    /**
     * 哪些属性类型支持
     *
     * @return
     */
    @Deprecated
    default Set<Class<?>> allowedPropertyTypes() {
        return null;
    }

    /**
     * 配置逻辑删除过滤和set
     *
     * @param builder
     */
    LogicDeleteMetadata configureBuild(LogicDeleteMetadataBuilder builder);

    /**
     * 是否接受当前拦截器
     *
     * @param entityClass
     * @return
     */
    boolean apply(@NotNull Class<?> entityClass);
}
