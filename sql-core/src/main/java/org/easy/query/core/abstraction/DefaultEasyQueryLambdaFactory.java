package org.easy.query.core.abstraction;

import org.easy.query.core.basic.api.context.SqlColumnPredicateContext;
import org.easy.query.core.expression.parser.abstraction.*;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.api.context.SelectContext;
import org.easy.query.core.basic.api.context.SqlPredicateContext;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.basic.sql.segment.segment.PredicateSegment;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlPredicateContext,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlPredicateContext sqlPredicateContext, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlPredicateContext,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,selectContext,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SelectContext selectContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,selectContext,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlPredicateContext sqlPredicateContext, SqlSegmentBuilder sqlSegmentBuilder) {
        return new DefaultSqlColumnSetter<T1>(index,sqlPredicateContext,sqlSegmentBuilder);
    }
}
