package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/6/1 08:40
 * 属性setter
 *
 * @author xuejiaming
 */
public interface ColumnSetter<T1> {
    TableAvailable getTable();

    /**
     * set column=val
     */
    default ColumnSetter<T1> set(String property, Object val) {
        return set(true, property, val);
    }

    /**
     * set column=val
     *
     * @param condition 执行条件
     * @param property  字段
     * @param val       值
     * @return children
     */
    ColumnSetter<T1> set(boolean condition, String property, Object val);

    default ColumnSetter<T1> setWithColumn(String property1, String property2) {
        return setWithColumn(true, property1, property2);
    }

    ColumnSetter<T1> setWithColumn(boolean condition, String property1, String property2);

    default ColumnSetter<T1> setIncrement(String property) {
        return setIncrement(true, property);
    }

    default ColumnSetter<T1> setIncrement(boolean condition, String property) {
        return setIncrementNumber(condition, property, 1);
    }

    default ColumnSetter<T1> setIncrement(String property, int val) {
        return setIncrementNumber(true, property, val);
    }

    default ColumnSetter<T1> setIncrement(boolean condition, String property, int val) {
        return setIncrementNumber(condition, property, val);
    }

    default ColumnSetter<T1> setIncrement(String property, long val) {
        return setIncrementNumber(true, property, val);
    }

    default ColumnSetter<T1> setIncrement(boolean condition, String property, long val) {
        return setIncrementNumber(condition, property, val);
    }


    default ColumnSetter<T1> setIncrement(String property, Number val) {
        return setIncrementNumber(true, property, val);
    }

    ColumnSetter<T1> setIncrementNumber(boolean condition, String property, Number val);

    default ColumnSetter<T1> setDecrement(String property) {
        return setDecrement(true, property);
    }

    default ColumnSetter<T1> setDecrement(boolean condition, String property) {
        return setDecrementNumber(condition, property, 1);
    }

    default ColumnSetter<T1> setDecrement(String property, int val) {
        return setDecrementNumber(true, property, val);
    }

    default ColumnSetter<T1> setDecrement(boolean condition, String property, int val) {
        return setDecrementNumber(condition, property, val);
    }

    default ColumnSetter<T1> setDecrement(String property, long val) {
        return setDecrementNumber(true, property, val);
    }

    default ColumnSetter<T1> setDecrement(boolean condition, String property, long val) {
        return setDecrementNumber(condition, property, val);
    }


    default ColumnSetter<T1> setDecrement(String property, Number val) {
        return setDecrementNumber(true, property, val);
    }

    ColumnSetter<T1> setDecrementNumber(boolean condition, String property, Number val);
}
