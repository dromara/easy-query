package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @author xuejiaming
 */
public interface QueryLambdaFactory {
    default <T1> SQLWherePredicate<T1> createSQLPredicate(EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment){
        return createSQLPredicate(0,sqlEntityExpression,predicateSegment);
    }
    default <T1> SQLColumnSelector<T1> createSQLColumnSelector(EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder){
        return createSQLColumnSelector(0,sqlEntityExpression,sqlSegmentBuilder);
    }
    default <T1> SQLColumnSelector<T1> createSQLColumnOrderSelector(EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc){
        return createSQLColumnOrderSelector(0,sqlEntityExpression,sqlSegmentBuilder,asc);
    }
    default <T1,TR>
    SQLColumnAsSelector<T1,TR> createSQLColumnAsSelector(EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass){
        return createSQLColumnAsSelector(0,sqlEntityExpression,sqlSegmentBuilder,resultClass);
    }
    <T1> SQLWherePredicate<T1> createSQLPredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SQLAggregatePredicate<T1> createSQLAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SQLColumnSelector<T1> createSQLColumnSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
    <T1> SQLColumnSelector<T1> createSQLColumnOrderSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc);
    <T1,TR> SQLColumnAsSelector<T1,TR> createSQLColumnAsSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass);
    <T1> SQLColumnSetter<T1> createSQLColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
    <T1> SQLColumnSelector<T1> createSQLColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
}
