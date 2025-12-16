package com.easy.query.test.conversion;

import com.easy.query.core.basic.extension.conversion.ValueAutoConverterProvider;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/12/16 20:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyValueAutoConverterProvider implements ValueAutoConverterProvider {
    @Override
    public boolean isSupport(@NotNull Class<?> clazz, @NotNull Class<?> propertyType) {
        return Enum.class.isAssignableFrom(propertyType)
                || IAutoRegister.class.isAssignableFrom(propertyType);
    }
}
