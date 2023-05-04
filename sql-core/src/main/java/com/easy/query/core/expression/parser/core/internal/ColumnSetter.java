package com.easy.query.core.expression.parser.core.internal;

import com.easy.query.core.expression.lambda.Property;

/**
 * @FileName: ColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:35
 * @author xuejiaming
 */
public interface ColumnSetter<T1,TChain> extends IndexAvailable {
    /**
     * set column=val
     */
    default TChain set(Property<T1, ?> column, Object val) {
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
    TChain set(boolean condition, Property<T1, ?> column, Object val);

    default TChain set(Property<T1, ?> column1, Property<T1, ?> column2) {
        return set(true, column1, column2);
    }

     TChain set(boolean condition, Property<T1, ?> column1, Property<T1, ?> column2);

    default  TChain setIncrement(Property<T1, Integer> column) {
        return setIncrement(true, column);
    }

    default TChain setIncrement(boolean condition,Property<T1, Integer> column){
        return setIncrementNumber(condition,column,1);
    }

    default  TChain setIncrement(Property<T1, Integer> column,int val) {
        return setIncrementNumber(true, column,val);
    }

    default TChain setIncrement(boolean condition,Property<T1, Integer> column,int val){
        return setIncrementNumber(condition,column,val);
    }

    default  TChain setIncrement(Property<T1, Long> column,long val) {
        return setIncrementNumber(true, column,val);
    }

    default TChain setIncrement(boolean condition,Property<T1, Long> column,long val){
        return setIncrementNumber(condition,column,val);
    }


    default  TChain setIncrement(Property<T1, ? extends Number> column,Number val) {
        return setIncrementNumber(true, column,val);
    }

    TChain setIncrementNumber(boolean condition,Property<T1, ? extends Number> column,Number val);

    default  TChain setDecrement(Property<T1, Integer> column) {
        return setDecrement(true, column);
    }

    default TChain setDecrement(boolean condition,Property<T1, Integer> column){
        return setDecrementNumber(condition,column,1);
    }

    default  TChain setDecrement(Property<T1, Integer> column,int val) {
        return setDecrementNumber(true, column,val);
    }

    default TChain setDecrement(boolean condition,Property<T1, Integer> column,int val){
        return setDecrementNumber(condition,column,val);
    }

    default  TChain setDecrement(Property<T1, Long> column,long val) {
        return setDecrementNumber(true, column,val);
    }

    default TChain setDecrement(boolean condition,Property<T1, Long> column,long val){
        return setDecrementNumber(condition,column,val);
    }


    default  TChain setDecrement(Property<T1, ? extends Number> column,Number val) {
        return setDecrementNumber(true, column,val);
    }

    TChain setDecrementNumber(boolean condition,Property<T1, ? extends Number> column,Number val);
}
