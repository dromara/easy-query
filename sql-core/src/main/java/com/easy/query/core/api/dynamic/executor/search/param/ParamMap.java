package com.easy.query.core.api.dynamic.executor.search.param;

import com.easy.query.core.api.dynamic.executor.search.util.EasyValueUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 参数字典，用于获取参数的键值对
 *
 * @author bkbits
 */
public interface ParamMap {
    /**
     * 创建一个可修改的参数字典实例
     *
     * @return 参数字典实例
     */
    static ParamMap create() {
        return new ParamHashMap();
    }

    /**
     * 创建只读参数字典
     *
     * @param paramMap 参数字典
     * @return 只读参数字典
     */
    static ParamMap unmodifiable(ParamMap paramMap) {
        if (paramMap == null) {
            return null;
        }
        if (paramMap instanceof UnmodifiableParamMap) {
            return paramMap;
        }
        return new UnmodifiableParamMap(paramMap);
    }


    /**
     * 获取当前页码，page永远大于等于1，默认为1
     *
     * @param pageNames 页面参数名称列表，可以为多个候选参数名
     * @return 当前页码，如果没有找到参数则返回1
     */
    default long getPage(String... pageNames) {
        Number number = null;
        for (String pageName : pageNames) {
            number = getParamNumber(pageName, null, Long::parseLong);
            if (number != null) {
                break;
            }
        }

        if (number == null) {
            return 1L;
        }
        long page = number.longValue();
        return page >= 1 ? page : 1L;
    }

    /**
     * 获取当前页码，page用于大于等于1，默认为1
     *
     * @return 当前页码
     */
    default long getPage() {
        return getPage("page", "current");
    }

    /**
     * 获取每页大小，pageSize用于大于等于1，默认为10
     *
     * @param pageSizeNames 页面尺寸参数名称列表，可以为多个候选参数名
     * @return 每页大小
     */
    default long getPageSize(String... pageSizeNames) {
        return getPage(pageSizeNames);
    }

    /**
     * 获取每页大小，pageSize用于大于等于1，默认为10
     *
     * @return 每页大小
     */
    default long getPageSize() {
        return getPageSize("pageSize");
    }

    /**
     * @param name 参数名
     * @return 参数字符串，如果参数不存在，则返回空字符串
     */
    default String getParamString(String name) {
        return getParamString(name, "");
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数字符串，如果参数不存在，则返回默认值
     */
    default String getParamString(String name, String defaultValue) {
        Object object = getParam(name);
        if (object == null) {
            return defaultValue;
        }
        return object.toString();
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数布尔值，如果参数不存在或者解析失败，则返回默认值
     */
    default boolean getParamBoolean(String name, boolean defaultValue) {
        Object object = getParam(name);
        return EasyValueUtil.getBoolean(object, defaultValue);
    }

    /**
     * 获取参数值，如果参数值是数字类型，则返回数字类型，否则使用提供的解析函数进行转换
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @param parser       解析函数，将字符串转换为数字
     * @return 参数值，如果参数不存在，则返回默认值
     */
    default Number getParamNumber(
            String name,
            Number defaultValue,
            Function<String, Number> parser
    ) {
        Object object = getParam(name);
        return EasyValueUtil.getNumber(object, defaultValue, parser);
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数值，如果参数不存在，则返回默认值
     */
    default int getParamInt(String name, int defaultValue) {
        return getParamNumber(name, defaultValue, Integer::parseInt).intValue();
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数值，如果参数不存在，则返回默认值
     */
    default long getParamLong(String name, long defaultValue) {
        return getParamNumber(name, defaultValue, Long::parseLong).longValue();
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数值，如果参数不存在，则返回默认值
     */
    default float getParamFloat(String name, float defaultValue) {
        return getParamNumber(name, defaultValue, Float::parseFloat).floatValue();
    }

    /**
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 参数值，如果参数不存在，则返回默认值
     */
    default double getParamDouble(String name, double defaultValue) {
        return getParamNumber(name, defaultValue, Double::parseDouble).doubleValue();
    }

    /**
     * @return 是否可写
     */
    boolean writable();

    /**
     * @param name 参数名
     * @return 第一个参数值
     */
    default Object getParam(String name) {
        List<Object> values = getParamValues(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    /**
     * @return 所有参数名的集合
     */
    Set<String> getParamNames();

    /**
     * @param name 参数名
     * @return 参数值列表
     */
    List<Object> getParamValues(String name);

    /**
     * @param name 参数名
     * @return 字符串类型参数值列表
     */
    default List<String> getParamStrings(String name) {
        List<Object> objects = getParamValues(name);
        if (objects == null) {
            return null;
        }
        return objects
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * 设置参数，如果参数已存在，则覆盖现有值
     *
     * @param name  参数名
     * @param value 参数值
     */
    ParamMap setParam(String name, Object value);

    /**
     * 设置参数列表，如果参数已存在，则覆盖现有值
     *
     * @param name  参数名
     * @param value 参数值列表
     */
    ParamMap setParam(String name, List<Object> value);

    /**
     * 设置参数列表，如果参数已存在，则覆盖现有值
     *
     * @param name   参数名
     * @param values 参数值列表
     */
    default ParamMap setParam(String name, Object... values) {
        return setParam(name, Arrays.asList(values));
    }

    /**
     * 添加参数到参数列表中，如果参数已存在，则追加到现有值后面
     *
     * @param name  参数名
     * @param value 参数值
     */
    ParamMap addParam(String name, Object value);

    /**
     * 清空参数
     */
    ParamMap clear();

    /**
     * 删除参数
     *
     * @param name 参数名
     */
    ParamMap removeParam(String name);


    /**
     * 将一个参数列表添加到参数字典中
     *
     * @param values 参数Map
     * @return this
     */
    @SuppressWarnings("unchecked")
    default ParamMap addParamMap(Map<String, Object> values) {
        if (!writable()) {
            throw new UnsupportedOperationException(getClass().getName() + " does not support " +
                                                            "writable");
        }

        values.forEach((key, value) -> {
            if (value instanceof List) {
                setParam(key, (List<Object>) value);
                return;
            }
            addParam(key, value);
        });
        return this;
    }
}
