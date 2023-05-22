package com.easy.query.test.conversion;

/**
 * create time 2023/5/22 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class EnumDeserializer {
    public static <T extends IEnum<T>> T deserialize(Class<T> enumClass, Integer integer) {
        for (T enumValue : enumClass.getEnumConstants()) {
            return enumValue.valueOf(integer);
        }
        throw new IllegalArgumentException("Invalid integer value for enum: " + integer);
    }
}
