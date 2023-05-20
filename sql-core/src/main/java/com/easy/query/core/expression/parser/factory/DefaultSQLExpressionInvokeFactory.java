package com.easy.query.core.expression.parser.factory;

import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProviderImpl;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSelector;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSQLAggregatePredicate;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnAsSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnSetSelector;
import com.easy.query.core.expression.parser.impl.DefaultSQLColumnSetter;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.DefaultSQLPredicate;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @FileName: DefaultEasyQueryLambdaFactory.java
 * @Description: 文件说明
 * @Date: 2023/2/14 08:33
 * @author xuejiaming
 */
public class DefaultSQLExpressionInvokeFactory implements SQLExpressionInvokeFactory {
    @Override
    public <T1> SQLWherePredicate<T1> createSQLPredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSQLPredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SQLAggregatePredicate<T1> createSQLAggregatePredicate(int index, EntityExpressionBuilder sqlEntityExpression, PredicateSegment predicateSegment) {
        return new DefaultSQLAggregatePredicate<>(index,sqlEntityExpression,predicateSegment);
    }

    @Override
    public <T1> SQLColumnSelector<T1> createSQLColumnSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
        return new DefaultSQLColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SQLColumnSelector<T1> createSQLColumnOrderSelector(int index, EntityQueryExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder, boolean asc) {
        return new DefaultSQLColumnSelector<>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1, TR> SQLColumnAsSelector<T1, TR> createSQLColumnAsSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegmentBuilder, Class<TR> resultClass) {
        return new DefaultSQLColumnAsSelector<T1,TR>(index, entityQueryExpressionBuilder,sqlSegmentBuilder,resultClass);
    }

    @Override
    public <T1> SQLColumnSetter<T1> createSQLColumnSetter(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
        return new DefaultSQLColumnSetter<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SQLColumnSelector<T1> createSQLColumnSetSelector(int index, EntityExpressionBuilder sqlEntityExpression, SQLBuilderSegment sqlSegmentBuilder) {
        return new DefaultSQLColumnSetSelector<T1>(index,sqlEntityExpression,sqlSegmentBuilder);
    }

    @Override
    public <T1> SQLExpressionProvider<T1> createSQLExpressionProvider(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        return new SQLExpressionProviderImpl<>(index,entityQueryExpressionBuilder);
    }
}
