package com.easy.query.core.proxy.core;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.builder.Setter;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.accpet.AggregatePredicateEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.IncludeEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.OrderByEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.PredicateEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.SetterEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;

/**
 * create time 2023/12/8 14:56
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySQLContext extends RuntimeContextAvailable {

    /**
     * 仅在where内可以获取否则为null
     *
     * @return
     */
    @Nullable
    Filter getFilter();

    @Nullable
    EntityExpressionBuilder getEntityExpressionBuilder();

    @Nullable
    default ExpressionContext getExpressionContext() {
        EntityExpressionBuilder entityExpressionBuilder = getEntityExpressionBuilder();
        if (entityExpressionBuilder != null) {
            return entityExpressionBuilder.getExpressionContext();
        }
        return null;
    }

    default EntitySQLContext getCurrentEntitySQLContext() {
        ValueHolder<EntitySQLContext> contextHolder = this.getContextHolder();
        if(contextHolder!=null){
            EntitySQLContext value = contextHolder.getValue();
            if(value!=null){
                return value;
            }
        }
        return this;
    }

    default void _createScope(SQLActionExpression sqlActionExpression) {
        ValueHolder<EntitySQLContext> contextHolder = this.getContextHolder();
        
        EntitySQLContext entitySQLContext = this;

        EntitySQLContext sqlContext = contextHolder.getValue();
        contextHolder.setValue(entitySQLContext);
        sqlActionExpression.apply();
        contextHolder.setValue(sqlContext);
    }

    @Nullable
    AggregateFilter getAggregateFilter();

    boolean methodIsInclude();

    @Nullable
    OrderSelector getOrderSelector();

    @Nullable
    SQLSelectAsExpression getSelectAsExpression();

    default void _include(SQLActionExpression sqlActionExpression) {
        accept(new IncludeEntityExpressionAcceptImpl(), sqlActionExpression);
    }

    default void _where(Filter filter, SQLActionExpression sqlActionExpression) {
        accept(new PredicateEntityExpressionAcceptImpl(filter), () -> {
            _createScope(sqlActionExpression);
        });
    }

    default void _whereOr(SQLActionExpression sqlActionExpression) {
        throw new UnsupportedOperationException();
    }

    default void _whereAnd(SQLActionExpression sqlActionExpression) {
        throw new UnsupportedOperationException();
    }

    default void _executeNativeSql(String sqlSegment, SQLActionExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        throw new UnsupportedOperationException();
    }

    default void _having(AggregateFilter aggregateFilter, SQLActionExpression sqlActionExpression) {
        accept(new AggregatePredicateEntityExpressionAcceptImpl(aggregateFilter), () -> {
            _createScope(sqlActionExpression);
        });
    }

    default void _orderBy(OrderSelector orderSelector, SQLActionExpression sqlActionExpression) {
        accept(new OrderByEntityExpressionAcceptImpl(orderSelector), () -> {
            _createScope(sqlActionExpression);
        });
    }

    default void _set(Setter setter, SQLActionExpression sqlActionExpression) {
        accept(new SetterEntityExpressionAcceptImpl(setter), sqlActionExpression);
    }

    void accept(EntityExpressionAccept accept, SQLActionExpression sqlActionExpression);

    void accept(SQLPredicateExpression sqlPredicateExpression);

    void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression);

    void accept(SQLColumnSetExpression sqlColumnSetExpression);

    void accept(SQLOrderByExpression sqlOrderByExpression);

    void accept(SQLSelectAsExpression... selectAsExpressions);



    default void setContextHolder(ValueHolder<EntitySQLContext> contextValueHolder){

    }

    default ValueHolder<EntitySQLContext> getContextHolder(){
        return null;
    }
}
