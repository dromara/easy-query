package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSqlAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnAsSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetter;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.DefaultSqlPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @author xuejiaming
 */
public class DefaultEasyQueryLambdaFactory implements EasyQueryLambdaFactory {
    @Override
    public <T1> SqlPredicate<T1> createSqlPredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSqlPredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSqlAggregatePredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc) {
        return new DefaultSqlColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SqlColumnAsSelector<T1, TR> createSqlColumnAsSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, Class<TR> resultClass) {
        return new DefaultSqlColumnAsSelector<T1,TR>(index,sqlEntityExpression,sqlSegmentBuilder,resultClass);
    }

    @Override
    public <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetter<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder) {
        return new DefaultSqlColumnSetSelector<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }
}
