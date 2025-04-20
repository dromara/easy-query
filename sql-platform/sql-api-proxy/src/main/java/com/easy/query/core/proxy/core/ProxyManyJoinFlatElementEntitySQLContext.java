package com.easy.query.core.proxy.core;

import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.impl.RewritePredicateToSelectProvider;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;

/**
 * create time 2023/12/8 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyManyJoinFlatElementEntitySQLContext implements FlatEntitySQLContext {
    private final RewritePredicateToSelectProvider<?, ?> rewritePredicateToSelectProvider;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final QueryRuntimeContext runtimeContext;
    private final SQLFuncExpression1<?, SQLSelectAsExpression> sqlSelectAsExpressionFunction;
    private final SQLExpression1<SQLPredicateExpression> sqlPredicateExpressionFunction;
    private ValueHolder<EntitySQLContext> contextValueHolder;

    public ProxyManyJoinFlatElementEntitySQLContext(RewritePredicateToSelectProvider<?, ?> rewritePredicateToSelectProvider, EntityExpressionBuilder entityExpressionBuilder, ValueHolder<EntitySQLContext> contextValueHolder, QueryRuntimeContext runtimeContext, SQLFuncExpression1<?, SQLSelectAsExpression> sqlSelectAsExpressionFunction, SQLExpression1<SQLPredicateExpression> sqlPredicateExpressionFunction) {
        this.rewritePredicateToSelectProvider = rewritePredicateToSelectProvider;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.contextValueHolder = contextValueHolder;

        this.runtimeContext = runtimeContext;
        this.sqlSelectAsExpressionFunction = sqlSelectAsExpressionFunction;
        this.sqlPredicateExpressionFunction = sqlPredicateExpressionFunction;
    }

    @Override
    public EntitySQLContext getCurrentEntitySQLContext() {
        return this;
    }

    @Override
    public void accept(EntityExpressionAccept accept, SQLActionExpression sqlActionExpression) {
        sqlActionExpression.apply();
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
//        sqlPredicateExpressionFunction.apply(sqlPredicateExpression);
        rewritePredicateToSelectProvider.flatElementFilterValue(sqlPredicateExpression).eq(true);
    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(SQLSelectAsExpression... selectAsExpressions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    @Override
    public Filter getFilter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }

    @Override
    public AggregateFilter getAggregateFilter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean methodIsInclude() {
        return false;
    }

    @Override
    public OrderSelector getOrderSelector() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLSelectAsExpression getSelectAsExpression() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLFuncExpression1<?, SQLSelectAsExpression> getSelectAsExpressionFunction() {
        return sqlSelectAsExpressionFunction;
    }

    @Override
    public void setContextHolder(ValueHolder<EntitySQLContext> contextValueHolder) {
        this.contextValueHolder = contextValueHolder;
    }

    @Override
    public ValueHolder<EntitySQLContext> getContextHolder() {
        return this.contextValueHolder;
    }
    //    @Override
//    public void _nativeSqlSegment(SQLActionExpression sqlActionExpression) {
//        1111
//    }
}
