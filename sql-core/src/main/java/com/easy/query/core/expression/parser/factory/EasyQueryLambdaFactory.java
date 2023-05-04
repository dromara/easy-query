package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.parser.core.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @author xuejiaming
 */
public interface EasyQueryLambdaFactory {
    default <T1> SqlPredicate<T1> createSqlPredicate(EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment){
        return createSqlPredicate(0,sqlEntityExpression,predicateSegment);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnSelector(EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder){
        return createSqlColumnSelector(0,sqlEntityExpression,sqlSegmentBuilder);
    }
    default <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc){
        return createSqlColumnOrderSelector(0,sqlEntityExpression,sqlSegmentBuilder,asc);
    }
    default <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, Class<TR> resultClass){
        return createSqlColumnAsSelector(0,sqlEntityExpression,sqlSegmentBuilder,resultClass);
    }
    <T1> SqlPredicate<T1> createSqlPredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SqlAggregatePredicate<T1> createSqlAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SqlColumnSelector<T1> createSqlColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnOrderSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, boolean asc);
    <T1,TR> SqlColumnAsSelector<T1,TR> createSqlColumnAsSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder, Class<TR> resultClass);
    <T1> SqlColumnSetter<T1> createSqlColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
    <T1> SqlColumnSelector<T1> createSqlColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SqlBuilderSegment sqlSegmentBuilder);
}
