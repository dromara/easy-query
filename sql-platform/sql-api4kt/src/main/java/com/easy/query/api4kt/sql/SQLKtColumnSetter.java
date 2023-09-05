package com.easy.query.api4kt.sql;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/1 08:40
 * 强类型setter
 *
 * @author xuejiaming
 */
public interface SQLKtColumnSetter<T1> extends EntitySQLTableOwner<T1> {
    default TableAvailable getTable() {
        return getColumnSetter().getTable();
    }

    ColumnSetter<T1> getColumnSetter();

    /**
     * set column=val
     */
    default <TProperty> SQLKtColumnSetter<T1> set(KProperty1<? super T1, TProperty> column, TProperty val) {
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
    default <TProperty> SQLKtColumnSetter<T1> set(boolean condition, KProperty1<? super T1, TProperty> column, TProperty val) {
        getColumnSetter().set(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default <TProperty> SQLKtColumnSetter<T1> setWithColumn(KProperty1<? super T1, TProperty> column1, KProperty1<? super T1, TProperty> column2) {
        return setWithColumn(true, column1, column2);
    }

    default <TProperty> SQLKtColumnSetter<T1> setWithColumn(boolean condition, KProperty1<? super T1, TProperty> column1, KProperty1<? super T1, TProperty> column2) {
        getColumnSetter().setWithColumn(condition, EasyKtLambdaUtil.getPropertyName(column1), EasyKtLambdaUtil.getPropertyName(column2));
        return this;
    }

    default SQLKtColumnSetter<T1> setIncrement(KProperty1<? super T1, Integer> column) {
        return setIncrement(true, column);
    }

    default SQLKtColumnSetter<T1> setIncrement(boolean condition, KProperty1<? super T1, Integer> column) {
        return setIncrementNumber(condition, column, 1);
    }

    default SQLKtColumnSetter<T1> setIncrement(KProperty1<? super T1, Integer> column, int val) {
        return setIncrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setIncrement(boolean condition, KProperty1<? super T1, Integer> column, int val) {
        return setIncrementNumber(condition, column, val);
    }

    default SQLKtColumnSetter<T1> setIncrement(KProperty1<? super T1, Long> column, long val) {
        return setIncrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setIncrement(boolean condition, KProperty1<? super T1, Long> column, long val) {
        return setIncrementNumber(condition, column, val);
    }


    default SQLKtColumnSetter<T1> setIncrement(KProperty1<? super T1, ? extends Number> column, Number val) {
        return setIncrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setIncrementNumber(boolean condition, KProperty1<? super T1, ? extends Number> column, Number val) {
        getColumnSetter().setIncrementNumber(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }

    default SQLKtColumnSetter<T1> setDecrement(KProperty1<? super T1, Integer> column) {
        return setDecrement(true, column);
    }

    default SQLKtColumnSetter<T1> setDecrement(boolean condition, KProperty1<? super T1, Integer> column) {
        return setDecrementNumber(condition, column, 1);
    }

    default SQLKtColumnSetter<T1> setDecrement(KProperty1<? super T1, Integer> column, int val) {
        return setDecrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setDecrement(boolean condition, KProperty1<? super T1, Integer> column, int val) {
        return setDecrementNumber(condition, column, val);
    }

    default SQLKtColumnSetter<T1> setDecrement(KProperty1<? super T1, Long> column, long val) {
        return setDecrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setDecrement(boolean condition, KProperty1<? super T1, Long> column, long val) {
        return setDecrementNumber(condition, column, val);
    }


    default SQLKtColumnSetter<T1> setDecrement(KProperty1<? super T1, ? extends Number> column, Number val) {
        return setDecrementNumber(true, column, val);
    }

    default SQLKtColumnSetter<T1> setDecrementNumber(boolean condition, KProperty1<? super T1, ? extends Number> column, Number val) {
        getColumnSetter().setDecrementNumber(condition, EasyKtLambdaUtil.getPropertyName(column), val);
        return this;
    }
}
