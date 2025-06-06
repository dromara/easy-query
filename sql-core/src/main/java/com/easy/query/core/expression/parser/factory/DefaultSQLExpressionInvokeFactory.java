package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProviderImpl;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.FilterImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.ColumnSetterImpl;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * create time 2023/2/14 08:33
 */
public class DefaultSQLExpressionInvokeFactory implements SQLExpressionInvokeFactory {
    @Override
    public <T1> WherePredicate<T1> createWherePredicate(TableAvailable table, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
        FilterImpl filter = new FilterImpl(sqlEntityExpression.getRuntimeContext(), sqlEntityExpression.getExpressionContext(), predicateSegment, false, sqlEntityExpression.getExpressionContext().getValueFilter());
        return new WherePredicateImpl<>(table, new FilterContext(filter,sqlEntityExpression));
//        return new SQLWherePredicateImpl<>(index,sqlEntityExpression,predicateSegment);
    }

//    @Override
//    public <T1> WhereAggregatePredicate<T1> createWhereAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
//        return new WhereAggregatePredicateImpl<>(index, sqlEntityExpression, predicateSegment);
//    }
//
//    @Override
//    public <T1> ColumnSelector<T1> createColumnSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
//        return new ColumnSelectorImpl<>(index,sqlEntityExpression,sqlSegmentBuilder);
//    }
//
//    @Override
//    public <T1> ColumnSelector<T1> createColumnOrderSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc) {
//        return new OrderColumnSelectorImpl<>(index,sqlEntityExpression,sqlSegmentBuilder,asc);
//    }

//    @Override
//    public <T1, TR> ColumnAsSelector<T1, TR> createColumnAsSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass) {
//        return new ColumnAsSelectorImpl<T1, TR>(index, entityQueryExpressionBuilder, sqlSegmentBuilder, resultClass);
//    }

    @Override
    public <T1> ColumnSetter<T1> createColumnSetter(TableAvailable table, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
        return new ColumnSetterImpl<T1>(table, sqlEntityExpression, sqlSegmentBuilder);
//        return new SQLColumnSetterImpl<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

//    @Override
//    public <T1> ColumnUpdateSetSelector<T1> createColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
//        return new ColumnUpdateSetSelectorImpl<T1>(sqlEntityExpression.getTable(index).getEntityTable(), sqlEntityExpression, sqlSegmentBuilder);
//    }

    @Override
    public <T1> SQLExpressionProvider<T1> createSQLExpressionProvider(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new SQLExpressionProviderImpl<>(index, entityQueryExpressionBuilder);
    }
}
