package org.easy.query.core.abstraction;

import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.impl.SqlPredicateContext;
import org.easy.query.core.expression.parser.impl.DefaultSqlAggregatePredicate;
import org.easy.query.core.expression.parser.impl.DefaultSqlColumnAsSelector;
import org.easy.query.core.expression.parser.impl.DefaultSqlColumnSelector;
import org.easy.query.core.expression.parser.impl.DefaultSqlPredicate;
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
}
