package com.easy.query.core.proxy.core;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;

/**
 * create time 2023/12/8 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyFlatElementEntitySQLContext implements FlatEntitySQLContext {
    private final SQLQueryable<?, ?> sqlQueryable;
    private final FilterContext whereFilterContext;
    private final ClientQueryable<?> clientQueryable;
    private final QueryRuntimeContext runtimeContext;
    private final SQLFuncExpression1<?, SQLSelectAsExpression> sqlSelectAsExpressionFunction;
    private ValueHolder<EntitySQLContext> contextValueHolder;

    public ProxyFlatElementEntitySQLContext(SQLQueryable<?, ?> sqlQueryable, ClientQueryable<?> clientQueryable, ValueHolder<EntitySQLContext> contextValueHolder, QueryRuntimeContext runtimeContext, SQLFuncExpression1<?, SQLSelectAsExpression> sqlSelectAsExpressionFunction) {
        this.sqlQueryable = sqlQueryable;
        this.whereFilterContext = clientQueryable == null ? null : clientQueryable.getSQLExpressionProvider1().getWhereFilterContext();
        this.clientQueryable = clientQueryable;
        this.contextValueHolder = contextValueHolder;

        this.runtimeContext = runtimeContext;
        this.sqlSelectAsExpressionFunction = sqlSelectAsExpressionFunction;
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
        sqlPredicateExpression.accept(whereFilterContext.getFilter());
        sqlQueryable.any();
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
        return whereFilterContext.getFilter();
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        //当是empty的时候没有clientQueryable
        if (clientQueryable == null) {
            return sqlQueryable.getEntitySQLContext().getEntityExpressionBuilder();
        }
        return clientQueryable.getSQLEntityExpressionBuilder();
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
    //    @Override
//    public void _nativeSqlSegment(SQLActionExpression sqlActionExpression) {
//        1111
//    }


    @Override
    public ValueHolder<EntitySQLContext> getContextHolder() {
        return contextValueHolder;
    }
}
