package org.jdqc.sql.core.abstraction.sql.base;

import org.jdqc.sql.core.abstraction.lambda.Property;

import java.io.Serializable;

/**
 *
 * @FileName: Predicate.java
 * @Description: 文件说明
 * @Date: 2023/2/5 09:09
 * @Created by xuejiaming
 */
public interface WherePredicate<T1, TChain> extends Serializable, IndexAware {
    /**
     * 等于 =
     */
    default TChain eq(Property<T1, ?> column, Object val) {
        return eq(true, column, val);
    }

    /**
     * 等于 =
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain eq(boolean condition, Property<T1, ?> column, Object val);
    /**
     * 等于 =
     */
    default TChain like(Property<T1, ?> column, Object val) {
        return like(true, column, val);
    }

    /**
     * 等于 =
     *
     * @param condition 执行条件
     * @param column    字段
     * @param val       值
     * @return children
     */
    TChain like(boolean condition, Property<T1, ?> column, Object val);

    default <T2, TChain2> TChain eq(WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        return eq(true, sub, column1, column2);
    }

    <T2, TChain2> TChain eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2);


    <T2, TChain2> WherePredicate<T2, TChain2> and(WherePredicate<T2, TChain2> sub);
    TChain and();
}
