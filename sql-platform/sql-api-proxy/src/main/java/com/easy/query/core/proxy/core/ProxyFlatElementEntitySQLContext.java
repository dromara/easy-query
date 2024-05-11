package com.easy.query.core.proxy.core;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;
import com.easy.query.core.proxy.sql.Select;

/**
 * create time 2023/12/8 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyFlatElementEntitySQLContext implements EntitySQLContext {
    private final SQLQueryable<?,?> sqlQueryable;
    private final FilterContext whereFilterContext;
    private final QueryRuntimeContext runtimeContext;
    private SQLSelectAsExpression sqlSelectAsExpression = null;

    public ProxyFlatElementEntitySQLContext(SQLQueryable<?,?> sqlQueryable, QueryRuntimeContext runtimeContext) {
        this.sqlQueryable = sqlQueryable;
        this.whereFilterContext = sqlQueryable.getQueryable().getClientQueryable().getSQLExpressionProvider1().getWhereFilterContext();

        this.runtimeContext = runtimeContext;
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
        if (sqlSelectAsExpression == null) {
            sqlSelectAsExpression = SQLSelectAsExpression.empty;
        }
        sqlSelectAsExpression = sqlSelectAsExpression._concat(Select.of(selectAsExpressions));
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
        return sqlQueryable.getQueryable().getSQLEntityExpressionBuilder();
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
        return sqlSelectAsExpression;
    }
    //    @Override
//    public void _nativeSqlSegment(SQLActionExpression sqlActionExpression) {
//        1111
//    }
}
