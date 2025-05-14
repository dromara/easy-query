package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: EasyQueryLambdaFactory.java
 * @Description: 文件说明
 * create time 2023/2/14 08:33
 */
public interface SQLExpressionInvokeFactory {
    <T1> WherePredicate<T1> createWherePredicate(TableAvailable table, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);

//    <T1> WhereAggregatePredicate<T1> createWhereAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment);

//    <T1> ColumnSelector<T1> createColumnSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);
//
//    <T1> ColumnSelector<T1> createColumnOrderSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc);

//    <T1, TR> ColumnAsSelector<T1, TR> createColumnAsSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass);

    <T1> ColumnSetter<T1> createColumnSetter(TableAvailable table, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);

//    <T1> ColumnUpdateSetSelector<T1> createColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder);

    <T1> SQLExpressionProvider<T1> createSQLExpressionProvider(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
