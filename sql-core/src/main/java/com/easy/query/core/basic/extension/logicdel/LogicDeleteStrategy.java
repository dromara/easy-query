package com.easy.query.core.basic.extension.logicdel;


import com.easy.query.core.metadata.LogicDeleteMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * @FileName: EasyLogicDeleteStrategy.java
 * @Description: 文件说明
 * create time 2023/3/7 07:59
 * @author xuejiaming
 */
public interface LogicDeleteStrategy {
    /**
     * 逻辑删除策略名称
     * @return
     */
    String getStrategy();

    /**
     * 配置逻辑删除过滤和set
     * @param builder
     */
    LogicDeleteMetadata configureBuild(LogicDeleteMetadataBuilder builder);

    /**
     * 是否接受当前拦截器
     * @param entityClass
     * @return
     */
    boolean apply(@NotNull Class<?> entityClass);
}
