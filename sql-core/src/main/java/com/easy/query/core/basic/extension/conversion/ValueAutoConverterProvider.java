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
     * @param clazz
     * @param propertyType
     * @return
     */
    boolean isSupport(@NotNull Class<?> clazz,@NotNull Class<?> propertyType);
}
