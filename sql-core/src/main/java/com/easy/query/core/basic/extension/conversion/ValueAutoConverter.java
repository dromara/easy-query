package com.easy.query.core.basic.extension.conversion;

import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/11/22 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueAutoConverter<TProperty, TProvider> extends ValueConverter<TProperty, TProvider> {
    /**
     * 判断是否需要转换
     * @param entityClass
     * @param propertyType
     * @param property
     * @return
     */
     boolean apply(@NotNull Class<?> entityClass, @NotNull Class<TProperty> propertyType, String property);
}
