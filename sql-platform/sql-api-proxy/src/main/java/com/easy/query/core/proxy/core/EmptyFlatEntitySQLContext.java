package com.easy.query.core.proxy.core;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;

/**
 * create time 2024/6/8 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyFlatEntitySQLContext implements FlatEntitySQLContext{
    private  String navValue;

    public EmptyFlatEntitySQLContext(String navValue){

        this.navValue = navValue;
    }
    @Override
    public String getNavValue() {
        return navValue;
    }

    @Override
    public void setNavValue(String navValue) {
        this.navValue=navValue;
    }

    @Override
    public SQLFuncExpression1<?, SQLSelectAsExpression> getSelectAsExpressionFunction() {
        return null;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return null;
    }

    @Nullable
    @Override
    public Filter getFilter() {
        return null;
    }

    @Nullable
    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return null;
    }

    @Nullable
    @Override
    public AggregateFilter getAggregateFilter() {
        return null;
    }

    @Override
    public boolean methodIsInclude() {
        return false;
    }

    @Nullable
    @Override
    public OrderSelector getOrderSelector() {
        return null;
    }

    @Nullable
    @Override
    public SQLSelectAsExpression getSelectAsExpression() {
        return null;
    }

    @Override
    public void accept(EntityExpressionAccept accept, SQLActionExpression sqlActionExpression) {

    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {

    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {

    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {

    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {

    }

    @Override
    public void accept(SQLSelectAsExpression... selectAsExpressions) {

    }
}
