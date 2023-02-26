package org.easy.query.core.expression.parser.abstraction.internal;

import org.easy.query.core.expression.lambda.Property;

/**
 * @FileName: ColumnSetter.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:35
 * @Created by xuejiaming
 */
public interface ColumnSetter<T1,TChain> extends IndexAware {
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

    default <T2, TChain2> TChain set(WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return set(true, sub, column1, column2);
    }

    <T2, TChain2> TChain set(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2);

    default  TChain setIncrement(Property<T1, Integer> column) {
        return setIncrement(true, column);
    }

    default TChain setIncrement(boolean condition,Property<T1, Integer> column){
        return setIncrement(condition,column,1);
    }

    default  TChain setIncrement(Property<T1, Integer> column,int val) {
        return setIncrement(true, column,val);
    }

    default TChain setIncrement(boolean condition,Property<T1, Integer> column,int val){
        return setIncrement(condition,column,String.valueOf(val));
    }

    default  TChain setIncrement(Property<T1, Long> column,long val) {
        return setIncrement(true, column,val);
    }

    default TChain setIncrement(boolean condition,Property<T1, Long> column,long val){
        return setIncrement(condition,column,String.valueOf(val));
    }


    default  TChain setIncrement(Property<T1, ? extends Number> column,String val) {
        return setIncrement(true, column,val);
    }

    TChain setIncrement(boolean condition,Property<T1, ? extends Number> column,String val);

    default  TChain setDecrement(Property<T1, Integer> column) {
        return setDecrement(true, column);
    }

    default TChain setDecrement(boolean condition,Property<T1, Integer> column){
        return setDecrement(condition,column,1);
    }

    default  TChain setDecrement(Property<T1, Integer> column,int val) {
        return setDecrement(true, column,val);
    }

    default TChain setDecrement(boolean condition,Property<T1, Integer> column,int val){
        return setDecrement(condition,column,String.valueOf(val));
    }

    default  TChain setDecrement(Property<T1, Long> column,long val) {
        return setDecrement(true, column,val);
    }

    default TChain setDecrement(boolean condition,Property<T1, Long> column,long val){
        return setDecrement(condition,column,String.valueOf(val));
    }


    default  TChain setDecrement(Property<T1, ? extends Number> column,String val) {
        return setDecrement(true, column,val);
    }

    TChain setDecrement(boolean condition,Property<T1, ? extends Number> column,String val);
}
