package com.easy.query.test.conversion;

import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyMapUtil;
import com.easy.query.test.annotation.EnumValue;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/10/17 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EnumValueDeserializer {
    private static final Map<String, Field> ENUM_TYPES = new ConcurrentHashMap<>();

    public static Object serialize(Enum<?> enumValue) {
        if (enumValue == null) {
            return null;
        }
        Optional<Field> codeOptional = getEnumValueField(enumValue.getClass());
        if (codeOptional.isPresent()) {
            Field filed = codeOptional.get();
            filed.setAccessible(true);
            try {
                return filed.get(enumValue);

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("Invalid integer value for enum: " + enumValue + ",from :" + EasyClassUtil.getInstanceSimpleName(enumValue));

    }

    public static <T extends Enum<?>> T deserialize(Class<T> enumClass, Integer code) {
        if (code == null) {
            return null;
        }
        Optional<Field> codeOptional = getEnumValueField(enumClass);
        if (codeOptional.isPresent()) {
            Field filed = codeOptional.get();
            T[] enumConstants = enumClass.getEnumConstants();
            for (T enumConstant : enumConstants) {
                filed.setAccessible(true);
                try {
                    if (Objects.equals(code, filed.get(enumConstant))) {
                        return enumConstant;
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        throw new IllegalArgumentException("Invalid integer value for enum: " + code + ",from :" + EasyClassUtil.getSimpleName(enumClass));

    }

    public static <T extends Enum<?>> Optional<Field> getEnumValueField(Class<T> enumClass) {
        if (enumClass != null && enumClass.isEnum()) {
            String className = enumClass.getName();
            Field s = EasyMapUtil.computeIfAbsent(ENUM_TYPES, className, key -> {
                Collection<Field> allFields = EasyClassUtil.getAllFields(enumClass);
                Optional<Field> optional = allFields.stream()
                        // 过滤包含注解@EnumValue的字段
                        .filter(field -> {
                            return field.isAnnotationPresent(EnumValue.class);
                        })
                        .findFirst();
                return optional.orElse(null);
            });
            return Optional.ofNullable(s);
        }
        return Optional.empty();

    }
}
