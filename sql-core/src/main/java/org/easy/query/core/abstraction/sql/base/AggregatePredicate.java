package org.easy.query.core.abstraction.sql.base;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.abstraction.sql.enums.EasyPredicate;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.abstraction.sql.enums.IEasyPredicate;

import java.io.Serializable;

/**
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 * @Created by xuejiaming
 */
public interface AggregatePredicate<T1, TChain> extends Serializable, IndexAware {
    default TChain avg(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return avg(true,column,predicate,val);
    }
    default TChain avg(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.MIN,column,predicate,val);
    }
    default TChain min(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return min(true,column,predicate,val);
    }
    default TChain min(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.MIN,column,predicate,val);
    }
    default TChain max(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return max(true,column,predicate,val);
    }
    default TChain max(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.MAX,column,predicate,val);
    }
    default TChain sum(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return sum(true,column,predicate,val);
    }
    default TChain sum(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.SUM,column,predicate,val);
    }
    default TChain countDistinct(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return countDistinct(true,column,predicate,val);
    }
    default TChain countDistinct(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.COUNT_DISTINCT,column,predicate,val);
    }
    default TChain count(Property<T1, ?> column, EasyPredicate predicate, Object val){
        return count(true,column,predicate,val);
    }
    default TChain count(boolean condition, Property<T1, ?> column, EasyPredicate predicate, Object val){
        return func(condition, EasyAggregate.COUNT,column,predicate,val);
    }
    TChain func(boolean condition, IEasyFunc easyAggregate, Property<T1, ?> column, IEasyPredicate predicate, Object val);


    <T2, TChain2> AggregatePredicate<T2, TChain2> then(AggregatePredicate<T2, TChain2> sub);
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
