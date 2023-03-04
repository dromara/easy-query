package org.easy.query.core.abstraction;

import org.easy.query.core.expression.context.SqlContext;
import org.easy.query.core.expression.parser.abstraction.*;
import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.parser.impl.*;
import org.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.query.SqlEntityExpression;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @Created by xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory{
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetter<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetSelector<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }
}
