package com.easy.query.core.abstraction;

import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.abstraction.*;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.query.SqlEntityExpression;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public interface EasyQueryLambdaFactory {
    default <T1> SqlPredicate<T1> createSqlPredicate(SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment){
        return createSqlPredicate(0,sqlEntityExpression,predicateSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder){
        return createSqlColumnSelector(0,sqlEntityExpression,sqlSegmentBuilder);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc){
        return createSqlColumnOrderSelector(0,sqlEntityExpression,sqlSegmentBuilder,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder){
        return createSqlColumnAsSelector(0,sqlEntityExpression,sqlSegmentBuilder);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
    <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
}
