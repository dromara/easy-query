package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.expression.parser.abstraction.*;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.segment.PredicateSegment;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public interface EasyQueryLambdaFactory {
    default <T1> SqlPredicate<T1> createSqlPredicate(SqlContext sqlContext,PredicateSegment predicateSegment){
        return createSqlPredicate(0,sqlContext,predicateSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder){
        return createSqlColumnSelector(0,sqlContext,sqlSegmentBuilder);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc){
        return createSqlColumnOrderSelector(0,sqlContext,sqlSegmentBuilder,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder){
        return createSqlColumnAsSelector(0,sqlContext,sqlSegmentBuilder);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlContext sqlContext, PredicateSegment predicateSegment);
    <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlContext sqlContext, PredicateSegment predicateSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index,SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index,SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder);
    <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlContext sqlContext,SqlSegmentBuilder sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, SqlContext sqlContext,SqlSegmentBuilder sqlSegmentBuilder);
}
