package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @author xuejiaming
 */
public interface SQLExpressionInvokeFactory {
    <T1> SQLWherePredicate<T1> createSQLPredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SQLAggregatePredicate<T1> createSQLAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);
    <T1> SQLColumnSelector<T1> createSQLColumnSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
    <T1> SQLColumnSelector<T1> createSQLColumnOrderSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc);
    <T1,TR> SQLColumnAsSelector<T1,TR> createSQLColumnAsSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass);
    <T1> SQLColumnSetter<T1> createSQLColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
    <T1> SQLColumnSelector<T1> createSQLColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
    <T1> SQLExpressionProvider<T1> createSQLExpressionProvider(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
