package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLSetLambdaNative;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;

/**
 * create time 2023/6/1 08:40
 * 强类型setter
 *
 * @author xuejiaming
 */
public interface SQLColumnSetter<T1> extends EntitySQLTableOwner<T1>, SQLSetLambdaNative<T1,SQLColumnSetter<T1>> {
    default TableAvailable getTable() {
        return getColumnSetter().getTable();
    }

    ColumnSetter<T1> getColumnSetter();

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
    default SQLColumnSetter<T1> set(boolean condition, Property<T1, ?> column, Object val) {
        getColumnSetter().set(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default SQLColumnSetter<T1> setWithColumn(Property<T1, ?> column1, Property<T1, ?> column2) {
        return setWithColumn(true, column1, column2);
    }

    default SQLColumnSetter<T1> setWithColumn(boolean condition, Property<T1, ?> column1, Property<T1, ?> column2) {
        getColumnSetter().setWithColumn(condition, EasyLambdaUtil.getPropertyName(column1), EasyLambdaUtil.getPropertyName(column2));
        return this;
    }

    default SQLColumnSetter<T1> setIncrement(Property<T1, Integer> column) {
        return setIncrement(true, column);
    }

    default SQLColumnSetter<T1> setIncrement(boolean condition, Property<T1, Integer> column) {
        return setIncrementNumber(condition, column, 1);
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

    default SQLColumnSetter<T1> setIncrement(boolean condition, Property<T1, Long> column, long val) {
        return setIncrementNumber(condition, column, val);
    }


    default SQLColumnSetter<T1> setIncrement(Property<T1, ? extends Number> column, Number val) {
        return setIncrementNumber(true, column, val);
    }

    default SQLColumnSetter<T1> setIncrementNumber(boolean condition, Property<T1, ? extends Number> column, Number val) {
        getColumnSetter().setIncrementNumber(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default SQLColumnSetter<T1> setDecrement(Property<T1, Integer> column) {
        return setDecrement(true, column);
    }

    default SQLColumnSetter<T1> setDecrement(boolean condition, Property<T1, Integer> column) {
        return setDecrementNumber(condition, column, 1);
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

    default SQLColumnSetter<T1> setDecrement(boolean condition, Property<T1, Long> column, long val) {
        return setDecrementNumber(condition, column, val);
    }


    default SQLColumnSetter<T1> setDecrement(Property<T1, ? extends Number> column, Number val) {
        return setDecrementNumber(true, column, val);
    }

    default SQLColumnSetter<T1> setDecrementNumber(boolean condition, Property<T1, ? extends Number> column, Number val) {
        getColumnSetter().setDecrementNumber(condition, EasyLambdaUtil.getPropertyName(column), val);
        return this;
    }
}
