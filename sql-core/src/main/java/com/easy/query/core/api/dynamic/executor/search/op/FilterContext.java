package com.easy.query.core.api.dynamic.executor.search.op;

import com.easy.query.core.api.dynamic.executor.search.util.EasyValueUtil;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.annotation.NotNull;

import java.util.List;

/**
 * 过滤上下文对象，包含过滤器，表、参数、列元数据
 *
 * @author bkbits
 */
public class FilterContext {
    private final Filter filter;
    private final TableAvailable table;
    private final List<Object> values;
    private final ColumnMetadata columnMetadata;

    public FilterContext(
            @NotNull Filter filter,
            @NotNull TableAvailable table,
            @NotNull List<Object> values,
            @NotNull ColumnMetadata columnMetadata
    ) {
        this.filter = filter;
        this.table = table;
        this.values = values;
        this.columnMetadata = columnMetadata;
    }

    /**
     * 根据范围过滤条件，构建过滤器。
     *
     * @param filter         当前过滤器
     * @param table          表信息
     * @param property       字段名称
     * @param reverse        是否反转条件
     * @param conditionLeft  是否有左边条件
     * @param valLeft        左边条件的值
     * @param conditionRight 是否有右边条件
     * @param valRight       右边条件的值
     * @param sqlRange       范围类型（闭区间、开区间等）
     */
    private static void filterRange(
            @NotNull Filter filter,
            @NotNull TableAvailable table,
            @NotNull String property,
            boolean reverse,
            boolean conditionLeft,
            @Nullable Object valLeft,
            boolean conditionRight,
            @Nullable Object valRight,
            @NotNull SQLRangeEnum sqlRange
    ) {
        if (!reverse) {
            if (conditionLeft) {
                if (sqlRange == SQLRangeEnum.CLOSED || sqlRange == SQLRangeEnum.CLOSED_OPEN) {
                    filter.ge(table, property, valLeft); // 左边是闭区间
                }
                else {
                    filter.gt(table, property, valLeft); // 左边是开区间
                }
            }

            if (conditionRight) {
                if (sqlRange == SQLRangeEnum.CLOSED || sqlRange == SQLRangeEnum.OPEN_CLOSED) {
                    filter.le(table, property, valRight); // 右边是闭区间
                }
                else {
                    filter.lt(table, property, valRight); // 右边是开区间
                }
            }
            return;
        }

        if (conditionLeft) {
            if (sqlRange == SQLRangeEnum.CLOSED || sqlRange == SQLRangeEnum.CLOSED_OPEN) {
                filter.lt(table, property, valLeft); // 左边是闭区间
            }
            else {
                filter.le(table, property, valLeft); // 左边是开区间
            }
        }

        if (conditionRight) {
            if (conditionLeft) {
                filter = filter.or();
            }
            if (sqlRange == SQLRangeEnum.CLOSED || sqlRange == SQLRangeEnum.OPEN_CLOSED) {
                filter.gt(table, property, valRight); // 右边是闭区间
            }
            else {
                filter.ge(table, property, valRight); // 右边是开区间
            }
        }
    }

    /**
     * 获取当前过滤器对象。
     *
     * @return 当前过滤器对象
     */
    public @NotNull Filter getFilter() {
        return filter;
    }

    /**
     * 获取当前表对象。
     *
     * @return 当前表对象
     */
    public @NotNull TableAvailable getTable() {
        return table;
    }

    /**
     * 获取列的名称。
     *
     * @return 列的名称
     */
    public @NotNull String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    /**
     * 获取第一个参数。
     *
     * @return 第一个参数值
     */
    public Object getParam() {
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    /**
     * 根据索引获取参数。
     *
     * @param index 参数索引
     * @return 参数值
     */
    public Object getParam(int index) {
        if (values == null || values.size() <= index) {
            return null;
        }
        return values.get(index);
    }

    /**
     * 获取所有参数。
     *
     * @return 参数列表
     */
    public List<Object> getParams() {
        return values;
    }

    /**
     * 获取默认值的字符串类型参数。
     *
     * @return 字符串类型的参数值
     */
    public String getParamString() {
        return getParamString(0, null);
    }

    /**
     * 获取默认值的字符串类型参数。
     *
     * @param defaultValue 默认值
     * @return 字符串类型的参数值
     */
    public String getParamString(String defaultValue) {
        return getParamString(0, defaultValue);
    }

    /**
     * 根据索引获取字符串类型参数（默认值为null）。
     *
     * @param index 参数索引
     * @return 字符串类型的参数值
     */
    public String getParamString(int index) {
        return getParamString(index, null);
    }

    /**
     * 根据索引获取字符串类型参数。
     *
     * @param index        参数索引
     * @param defaultValue 默认值
     * @return 字符串类型的参数值
     */
    public String getParamString(int index, String defaultValue) {
        return EasyValueUtil.getString(getParam(index), defaultValue);
    }

    /**
     * 获取默认值的整数类型参数。
     *
     * @param defaultValue 默认值
     * @return 整数类型的参数值
     */
    public int getParamInt(int defaultValue) {
        return getParamInt(0, defaultValue);
    }

    /**
     * 根据索引获取整数类型参数。
     *
     * @param index        参数索引
     * @param defaultValue 默认值
     * @return 整数类型的参数值
     */
    public int getParamInt(int index, int defaultValue) {
        return EasyValueUtil.getInt(getParam(index), defaultValue);
    }

    /**
     * 获取默认值的长整型参数。
     *
     * @param defaultValue 默认值
     * @return 长整型的参数值
     */
    public long getParamLong(long defaultValue) {
        return getParamLong(0, defaultValue);
    }

    /**
     * 根据索引获取长整型参数。
     *
     * @param index        参数索引
     * @param defaultValue 默认值
     * @return 长整型的参数值
     */
    public long getParamLong(int index, long defaultValue) {
        return EasyValueUtil.getLong(getParam(index), defaultValue);
    }

    /**
     * 获取默认值的浮点型参数。
     *
     * @param defaultValue 默认值
     * @return 浮点型的参数值
     */
    public float getParamFloat(float defaultValue) {
        return getParamFloat(0, defaultValue);
    }

    /**
     * 根据索引获取浮点型参数。
     *
     * @param index        参数索引
     * @param defaultValue 默认值
     * @return 浮点型的参数值
     */
    public float getParamFloat(int index, float defaultValue) {
        return EasyValueUtil.getFloat(getParam(index), defaultValue);
    }

    /**
     * 获取默认值的双精度浮点型参数。
     *
     * @param defaultValue 默认值
     * @return 双精度浮点型的参数值
     */
    public double getParamDouble(double defaultValue) {
        return getParamDouble(0, defaultValue);
    }

    /**
     * 根据索引获取双精度浮点型参数。
     *
     * @param index        参数索引
     * @param defaultValue 默认值
     * @return 双精度浮点型的参数值
     */
    public double getParamDouble(int index, double defaultValue) {
        return EasyValueUtil.getDouble(getParam(index), defaultValue);
    }

    /**
     * 根据条件范围设置过滤条件。
     *
     * @param table          表对象
     * @param property       字段名
     * @param conditionLeft  左边条件是否存在
     * @param valLeft        左边条件的值
     * @param conditionRight 右边条件是否存在
     * @param valRight       右边条件的值
     * @param sqlRange       范围类型（闭区间、开区间等）
     */
    public void range(
            @NotNull TableAvailable table,
            @NotNull String property,
            boolean conditionLeft,
            @Nullable Object valLeft,
            boolean conditionRight,
            @Nullable Object valRight,
            @NotNull SQLRangeEnum sqlRange
    ) {
        if (!conditionLeft && !conditionRight) {
            return;
        }
        Filter filter = getFilter();
        if (conditionLeft && conditionRight) {
            filter.and(t -> filterRange(
                    t,
                    table,
                    property,
                    false,
                    conditionLeft,
                    valLeft,
                    conditionRight,
                    valRight,
                    sqlRange
            ));
            return;
        }
        filterRange(
                filter,
                table,
                property,
                false,
                conditionLeft,
                valLeft,
                conditionRight,
                valRight,
                sqlRange
        );
    }

    /**
     * 根据条件范围设置过滤条件。
     *
     * @param table          表对象
     * @param property       字段名
     * @param conditionLeft  左边条件是否存在
     * @param valLeft        左边条件的值
     * @param conditionRight 右边条件是否存在
     * @param valRight       右边条件的值
     * @param sqlRange       范围类型（闭区间、开区间等）
     */
    public void notRange(
            @NotNull TableAvailable table,
            @NotNull String property,
            boolean conditionLeft,
            @Nullable Object valLeft,
            boolean conditionRight,
            @Nullable Object valRight,
            @NotNull SQLRangeEnum sqlRange
    ) {
        if (!conditionLeft && !conditionRight) {
            return;
        }
        Filter filter = getFilter();
        if (conditionLeft && conditionRight) {
            filter.and(t -> filterRange(
                    t,
                    table,
                    property,
                    true,
                    conditionLeft,
                    valLeft,
                    conditionRight,
                    valRight,
                    sqlRange
            ));
            return;
        }
        filterRange(
                filter,
                table,
                property,
                true,
                conditionLeft,
                valLeft,
                conditionRight,
                valRight,
                sqlRange
        );
    }
}