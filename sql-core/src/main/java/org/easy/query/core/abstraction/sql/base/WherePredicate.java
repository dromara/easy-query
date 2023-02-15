package org.easy.query.core.abstraction.sql.base;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;

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


    <T2, TChain2> WherePredicate<T2, TChain2> then(WherePredicate<T2, TChain2> sub);
    default TChain and(){
        return and(true);
    }
    TChain and(boolean condition);
    default TChain and(SqlExpression<TChain> predicateSqlExpression){
        return and(true,predicateSqlExpression);
    }
    TChain and(boolean condition,SqlExpression<TChain> predicateSqlExpression);
    default TChain or(){
        return or(true);
    }
    TChain or(boolean condition);

    default TChain or(SqlExpression<TChain> predicateSqlExpression){
        return or(true,predicateSqlExpression);
    }
    TChain or(boolean condition,SqlExpression<TChain> predicateSqlExpression);
}
