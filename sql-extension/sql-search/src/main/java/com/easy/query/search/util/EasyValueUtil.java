package com.easy.query.search.util;

import java.util.function.Function;

/**
 * 提供一组方法来获取不同类型的参数值，包括字符串、数字、布尔值等。
 * 支持根据索引获取参数值，并提供默认值处理。
 *
 * @author bkbits
 */
public abstract class EasyValueUtil {
    /**
     * 获取指定索引的字符串类型参数值，若为 null 则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 字符串类型的参数值
     */
    public static String getString(Object param, String defaultValue) {
        if (param == null) {
            return defaultValue.trim();
        }
        return param.toString().trim();
    }

    /**
     * 获取指定索引的数字类型参数值，若为 null 则返回默认值，若无法转换则通过 parser 转换
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @param parser       解析器
     * @return 数字类型的参数值
     */
    public static Number getNumber(
            Object param,
            Number defaultValue,
            Function<String, Number> parser
    ) {
        if (param == null) {
            return defaultValue;
        }

        if (param instanceof Number) {
            return (Number) param;
        }

        return parser.apply(param.toString());
    }

    /**
     * 获取指定索引的整数类型参数值，若无法转换则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 整数类型的参数值
     */
    public static int getInt(Object param, int defaultValue) {
        return getNumber(param, defaultValue, Integer::parseInt).intValue();
    }

    /**
     * 获取指定索引的长整型参数值，若无法转换则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 长整型的参数值
     */
    public static long getLong(Object param, long defaultValue) {
        return getNumber(param, defaultValue, Long::parseLong).longValue();
    }

    /**
     * 获取指定索引的浮点型参数值，若无法转换则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 浮点型的参数值
     */
    public static float getFloat(Object param, float defaultValue) {
        return getNumber(param, defaultValue, Float::parseFloat).floatValue();
    }

    /**
     * 获取指定索引的双精度浮点型参数值，若无法转换则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 双精度浮点型的参数值
     */
    public static double getDouble(Object param, double defaultValue) {
        return getNumber(param, defaultValue, Double::parseDouble).doubleValue();
    }

    /**
     * 获取指定索引的布尔型参数值，若为 null 或无法转换则返回默认值
     *
     * @param param        参数
     * @param defaultValue 默认值
     * @return 布尔型的参数值
     */
    public static boolean getBoolean(Object param, boolean defaultValue) {
        if (param == null) {
            return defaultValue;
        }
        if (param instanceof Number) {
            return ((Number) param).intValue() != 0;
        }
        if (param instanceof Boolean) {
            return (Boolean) param;
        }

        return Boolean.parseBoolean(param.toString());
    }
}
