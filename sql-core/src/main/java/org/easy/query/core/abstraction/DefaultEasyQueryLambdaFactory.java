package org.easy.query.core.abstraction;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.expression.parser.abstraction.*;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.SqlEntityExpressionSegment;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlEntityExpressionSegment sqlEntityExpressionSegment, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlEntityExpressionSegment,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlContext sqlContext, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlContext,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetter<T1>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, SqlContext sqlContext, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetSelector<T1>(index,sqlContext,sqlSegmentBuilder);
    }
}
