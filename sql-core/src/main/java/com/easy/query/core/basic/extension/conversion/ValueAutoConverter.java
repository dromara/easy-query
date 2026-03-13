package com.easy.query.core.basic.extension.conversion;

import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/11/22 21:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueAutoConverter<TProperty, TProvider> extends ValueConverter<TProperty, TProvider> {
    default boolean apply(@NotNull Class<?> entityClass, @NotNull Class<TProperty> propertyType, String property) {
        return apply(entityClass, propertyType);
    }

    /**
     * 后续作废请使用{@link ValueAutoConverter#apply(Class, Class, String)}
     * @param entityClass
     * @param propertyType
     * @return
     */
    @Deprecated
    boolean apply(@NotNull Class<?> entityClass, @NotNull Class<TProperty> propertyType);
}
