package com.easy.query.core.basic.extension.conversion;

import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/12/16 20:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultValueAutoConverterProvider implements ValueAutoConverterProvider{
    @Override
    public boolean isSupport(@NotNull Class<?> clazz, @NotNull Class<?> propertyType) {
        return Enum.class.isAssignableFrom(propertyType);
    }
}
