package com.easy.query.core.basic.extension.conversion;

import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/12/16 20:02
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ValueAutoConverterProvider {
    /**
     * 当前类型是否支持{@link ValueAutoConverter}
     *
     * 后续作废使用{@link ValueAutoConverterProvider#isSupport(Class, Class, String)}
     * @param clazz
     * @param propertyType
     * @return
     */
    @Deprecated
    boolean isSupport(@NotNull Class<?> clazz, @NotNull Class<?> propertyType);

    default boolean isSupport(@NotNull Class<?> clazz, @NotNull Class<?> propertyType, String property) {
        return isSupport(clazz, propertyType);
    }
}
