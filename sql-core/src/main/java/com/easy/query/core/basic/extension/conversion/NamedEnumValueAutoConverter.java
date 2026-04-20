package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.annotation.Enumerated;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2026/4/19 20:28
 * 枚举名称转换器
 *
 * @author xuejiaming
 */
public class NamedEnumValueAutoConverter implements ValueAutoConverter<Enum<?>, Object> {

    public static final NamedEnumValueAutoConverter INSTANCE = new NamedEnumValueAutoConverter();
    private static final Map<Class<?>/*enum class*/, Map<String, Enum<?>>> NAME_CACHE = new ConcurrentHashMap<>();
    @Override
    public boolean apply(@NotNull Class<?> entityClass, @NotNull Class<Enum<?>> propertyType, String property) {
        //枚举类型并且field带有注解
        return Enum.class.isAssignableFrom(propertyType) && propertyType.isAnnotationPresent(Enumerated.class);
    }

    @Nullable
    @Override
    public Object serialize(@Nullable Enum<?> anEnum, @NotNull ColumnMetadata columnMetadata) {
        //将枚举转成字符串

        if (anEnum == null) {
            return null;
        }

        return anEnum.name();
    }

    @Nullable
    @Override
    public Enum<?> deserialize(@Nullable Object dbValue, @NotNull ColumnMetadata columnMetadata) {
        //将字符串转成枚举
        if(dbValue==null){
            return null;
        }
        if (!(dbValue instanceof String)) {
            throw new UnsupportedOperationException("Expected String but got: " + dbValue);
        }

        return getByName(columnMetadata.getPropertyType(), (String) dbValue);
    }
    private Enum<?> getByName(Class<?> enumClass, String name) {
        Map<String, Enum<?>> map = NAME_CACHE.computeIfAbsent(enumClass, cls -> {
            Map<String, Enum<?>> m = new HashMap<>();
            for (Object constant : cls.getEnumConstants()) {
                Enum<?> e = (Enum<?>) constant;
                m.put(e.name(), e);
            }
            return m;
        });

        Enum<?> result = map.get(name);
        if (result == null) {
            throw new IllegalArgumentException("Unknown enum name: " + name + " for " + enumClass);
        }
        return result;
    }
}
