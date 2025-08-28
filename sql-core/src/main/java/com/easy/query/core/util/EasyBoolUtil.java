package com.easy.query.core.util;

/**
 * create time 2025/8/28 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyBoolUtil {
    private EasyBoolUtil() {
    }

    /**
     * 判断是否为 true
     */
    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    /**
     * 判断是否为 false
     */
    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    /**
     * 判断是否不是 true (包含 null 和 false)
     */
    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    /**
     * 判断是否不是 false (包含 null 和 true)
     */
    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }

    /**
     * 将 Boolean 转换为 boolean，null 按 false 处理
     */
    public static boolean toPrimitive(Boolean value) {
        return value != null && value;
    }

    /**
     * 将 Boolean 转换为 boolean，null 按指定默认值处理
     */
    public static boolean toPrimitive(Boolean value, boolean defaultValue) {
        return value != null ? value : defaultValue;
    }
}
