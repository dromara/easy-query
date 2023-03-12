package com.easy.query.core.expression.parser.abstraction.internal;

import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.enums.IEasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.enums.SqlPredicateCompare;

/**
 * @FileName: AggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:17
 * @Created by xuejiaming
 */
public interface AggregatePredicate<T1, TChain> extends  IndexAware {
    default TChain avg(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return avg(true,column,compare,val);
    }
    default TChain avg(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return func(condition, EasyAggregate.MIN,column,compare,val);
    }
    default TChain min(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return min(true,column,compare,val);
    }
    default TChain min(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return func(condition, EasyAggregate.MIN,column,compare,val);
    }
    default TChain max(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return max(true,column,compare,val);
    }
    default TChain max(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return func(condition, EasyAggregate.MAX,column,compare,val);
    }
    default TChain sum(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return sum(true,column,compare,val);
    }
    default TChain sum(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return func(condition, EasyAggregate.SUM,column,compare,val);
    }
    default TChain countDistinct(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return countDistinct(true,column,compare,val);
    }
    default TChain countDistinct(boolean condition, Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return func(condition, EasyAggregate.COUNT_DISTINCT,column,compare,val);
    }
    default TChain count(Property<T1, ?> column, AggregatePredicateCompare compare, Object val){
        return count(true,column,compare,val);
    }
    default TChain count(boolean condition, Property<T1, ?> column, SqlPredicateCompare compare, Object val){
        return func(condition, EasyAggregate.COUNT,column,compare,val);
    }
    TChain func(boolean condition, IEasyFunc easyAggregate, Property<T1, ?> column, SqlPredicateCompare compare, Object val);


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
