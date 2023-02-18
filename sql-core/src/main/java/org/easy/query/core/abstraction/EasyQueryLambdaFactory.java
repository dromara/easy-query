package org.easy.query.core.abstraction;

import org.easy.query.core.abstraction.sql.base.SqlAggregatePredicate;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.segments.PredicateSegment;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public interface EasyQueryLambdaFactory {
    default <T1> SqlPredicate<T1> createSqlPredicate(SelectContext selectContext,PredicateSegment predicateSegment){
        return createSqlPredicate(0,selectContext,predicateSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder){
        return createSqlColumnSelector(0,selectContext,sqlSegmentBuilder);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder,boolean asc){
        return createSqlColumnOrderSelector(0,selectContext,sqlSegmentBuilder,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder){
        return createSqlColumnAsSelector(0,selectContext,sqlSegmentBuilder);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, SelectContext selectContext, PredicateSegment predicateSegment);
    <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SelectContext selectContext, PredicateSegment predicateSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index,SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index,SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder,boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index,SelectContext selectContext, SqlSegment0Builder sqlSegmentBuilder);
}
