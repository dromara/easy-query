package com.easy.query.core.expression.parser.core;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * @FileName: ColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:35
 * @author xuejiaming
 */
public interface SQLColumnSetter<T1> {
    TableAvailable getTable();
    /**
     * set column=val
     */
    default SQLColumnSetter<T1> set(Property<T1, ?> column, Object val) {
        return set(true, column, val);
    }

    /**
     * set column=val
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    SQLColumnSetter<T1> set(boolean condition, Property<T1, ?> column, Object val);

    default SQLColumnSetter<T1> set(Property<T1, ?> column1, Property<T1, ?> column2) {
        return set(true, column1, column2);
    }

     SQLColumnSetter<T1> set(boolean condition, Property<T1, ?> column1, Property<T1, ?> column2);

    default SQLColumnSetter<T1> setIncrement(Property<T1, Integer> column) {
        return setIncrement(true, column);
    }

    default SQLColumnSetter<T1> setIncrement(boolean condition, Property<T1, Integer> column){
        return setIncrementNumber(condition,column,1);
    }

    default SQLColumnSetter<T1> setIncrement(Property<T1, Integer> column, int val) {
        return setIncrementNumber(true, column,val);
    }

    default SQLColumnSetter<T1> setIncrement(boolean condition, Property<T1, Integer> column, int val){
        return setIncrementNumber(condition,column,val);
    }

    default SQLColumnSetter<T1> setIncrement(Property<T1, Long> column, long val) {
        return setIncrementNumber(true, column,val);
    }

    default SQLColumnSetter<T1> setIncrement(boolean condition, Property<T1, Long> column, long val){
        return setIncrementNumber(condition,column,val);
    }


    default SQLColumnSetter<T1> setIncrement(Property<T1, ? extends Number> column, Number val) {
        return setIncrementNumber(true, column,val);
    }

    SQLColumnSetter<T1> setIncrementNumber(boolean condition, Property<T1, ? extends Number> column, Number val);

    default SQLColumnSetter<T1> setDecrement(Property<T1, Integer> column) {
        return setDecrement(true, column);
    }

    default SQLColumnSetter<T1> setDecrement(boolean condition, Property<T1, Integer> column){
        return setDecrementNumber(condition,column,1);
    }

    default SQLColumnSetter<T1> setDecrement(Property<T1, Integer> column, int val) {
        return setDecrementNumber(true, column,val);
    }

    default SQLColumnSetter<T1> setDecrement(boolean condition, Property<T1, Integer> column, int val){
        return setDecrementNumber(condition,column,val);
    }

    default SQLColumnSetter<T1> setDecrement(Property<T1, Long> column, long val) {
        return setDecrementNumber(true, column,val);
    }

    default SQLColumnSetter<T1> setDecrement(boolean condition, Property<T1, Long> column, long val){
        return setDecrementNumber(condition,column,val);
    }


    default SQLColumnSetter<T1> setDecrement(Property<T1, ? extends Number> column, Number val) {
        return setDecrementNumber(true, column,val);
    }

    SQLColumnSetter<T1> setDecrementNumber(boolean condition, Property<T1, ? extends Number> column, Number val);
}
