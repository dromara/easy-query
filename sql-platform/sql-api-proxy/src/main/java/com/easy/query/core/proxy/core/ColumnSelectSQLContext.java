package com.easy.query.core.proxy.core;

import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.SQLColumnSetExpression;
import com.easy.query.core.proxy.SQLOrderByExpression;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.accpet.AggregatePredicateEntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.OrderByEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.PredicateEntityExpressionAccept;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/27 20:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnSelectSQLContext implements EntitySQLContext {
    private EntityExpressionAccept accept = EntityExpressionAccept.empty;

    private SQLSelectAsExpression sqlSelectAsExpression = null;

    @Override
    public void accept(EntityExpressionAccept accept, SQLActionExpression sqlActionExpression) {
        EntityExpressionAccept tempAccept = this.accept;
        this.accept = accept;
        sqlActionExpression.apply();
        this.accept = tempAccept;
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {
        if (sqlSelectAsExpression == null) {
            sqlSelectAsExpression = SQLSelectAsExpression.empty;
        }
        sqlSelectAsExpression = sqlSelectAsExpression._concat(new SQLSelectAsImpl(sqlColumnSetExpression::accept, sqlColumnSetExpression::accept, s -> {
            throw new UnsupportedOperationException();
        }));
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
    public String getNavValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNavValue(String navValue) {
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return EmptyQueryRuntimeContext.DEFAULT;
    }


    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return null;
    }

    @Override
    public AggregateFilter getAggregateFilter() {
        return null;
    }

    @Override
    public boolean methodIsInclude() {
        return false;
    }

    @Override
    public OrderSelector getOrderSelector() {
        return null;
    }

    @Override
    public SQLSelectAsExpression getSelectAsExpression() {
        return sqlSelectAsExpression;
    }

    @Override
    public void _whereOr(SQLActionExpression sqlActionExpression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void _whereAnd(SQLActionExpression sqlActionExpression) {
        throw new UnsupportedOperationException();
    }
    //    @Override
//    public void _nativeSqlSegment(SQLActionExpression sqlActionExpression) {
//        1111
//    }

    @Override
    public void _executeNativeSql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {

        if (accept instanceof PredicateEntityExpressionAccept) {
            accept.accept(new SQLPredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            }));
        } else if (accept instanceof AggregatePredicateEntityExpressionAccept) {
            accept.accept(new SQLAggregatePredicateImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            }, f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            }));
        } else if (accept instanceof OrderByEntityExpressionAcceptImpl) {
            accept.accept(new SQLOrderSelectImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            }));
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
