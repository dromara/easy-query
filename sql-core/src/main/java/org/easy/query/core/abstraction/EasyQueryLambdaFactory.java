package org.easy.query.core.abstraction;

import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlPredicateContext;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;

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
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder){
        return createSqlColumnSelector(0,selectContext,sqlSegmentBuilder);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc){
        return createSqlColumnOrderSelector(0,selectContext,sqlSegmentBuilder,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder){
        return createSqlColumnAsSelector(0,selectContext,sqlSegmentBuilder);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment);
    <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index,SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index,SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder);
}
