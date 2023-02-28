package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.expression.parser.abstraction.*;
import org.easy.query.core.expression.builder.SqlSegmentBuilder;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.expression.segment.PredicateSegment;
import org.easy.query.core.expression.segment.predicate.DefaultSqlPredicate;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlContext sqlPredicateContext, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlPredicateContext,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlContext sqlContext, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlContext,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnSetter<T1>(index,sqlContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnSetSelector<T1>(index,sqlContext,sqlSegmentBuilder);
    }
}
