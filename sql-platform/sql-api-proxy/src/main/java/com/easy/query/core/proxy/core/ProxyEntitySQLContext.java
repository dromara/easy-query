package com.easy.query.core.proxy.core;

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
import com.easy.query.core.proxy.core.accpet.AggregatePredicateEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.EntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.OrderByEntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.OrderByEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.core.accpet.PredicateEntityExpressionAccept;
import com.easy.query.core.proxy.core.accpet.PredicateEntityExpressionAcceptImpl;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.impl.SQLOrderSelectImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/8 15:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyEntitySQLContext implements EntitySQLContext {
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final QueryRuntimeContext runtimeContext;
    private EntityExpressionAccept accept = EntityExpressionAccept.empty;
    private SQLSelectAsExpression sqlSelectAsExpression = null;

    public ProxyEntitySQLContext(EntityExpressionBuilder entityExpressionBuilder,QueryRuntimeContext runtimeContext) {
        this.entityExpressionBuilder = entityExpressionBuilder;

        this.runtimeContext = runtimeContext;
    }

    @Override
    public void accept(EntityExpressionAccept accept, SQLActionExpression sqlActionExpression) {
        EntityExpressionAccept tempAccept = this.accept;
        this.accept = accept;
        sqlActionExpression.apply();
        this.accept = tempAccept;
    }

    @Override
    public void accept(SQLPredicateExpression sqlPredicateExpression) {
        accept.accept(sqlPredicateExpression);

    }

    @Override
    public void accept(SQLAggregatePredicateExpression sqlAggregatePredicateExpression) {
        accept.accept(sqlAggregatePredicateExpression);

    }

    @Override
    public void accept(SQLColumnSetExpression sqlColumnSetExpression) {
        accept.accept(sqlColumnSetExpression);
    }

    @Override
    public void accept(SQLOrderByExpression sqlOrderByExpression) {
        accept.accept(sqlOrderByExpression);
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
        if (accept instanceof PredicateEntityExpressionAccept) {
            return ((PredicateEntityExpressionAccept) accept).getFilter();
        }
        return null;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return entityExpressionBuilder;
    }

    @Override
    public AggregateFilter getAggregateFilter() {
        if (accept instanceof AggregatePredicateEntityExpressionAccept) {
            return ((AggregatePredicateEntityExpressionAccept) accept).getAggregateFilter();
        }
        return null;
    }

    @Override
    public OrderSelector getOrderSelector() {
        if (accept instanceof OrderByEntityExpressionAccept) {
            return ((OrderByEntityExpressionAccept) accept).getOrderSelector();
        }
        return null;
    }

    @Override
    public SQLSelectAsExpression getSelectAsExpression() {
        return sqlSelectAsExpression;
    }

    @Override
    public void _whereOr(SQLActionExpression sqlActionExpression) {
        if (accept instanceof PredicateEntityExpressionAccept) {
            PredicateEntityExpressionAccept predicateEntityExpressionAccept = (PredicateEntityExpressionAccept) accept;
            boolean nextIsOr = predicateEntityExpressionAccept.nextIsOr();
            Filter filter = predicateEntityExpressionAccept.getFilter();
            if(nextIsOr){
                filter.or(f -> {
                    PredicateEntityExpressionAcceptImpl innerAccept = new PredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(true);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(true);
                });
                filter.or();
            }else{
                filter.and(f -> {
                    PredicateEntityExpressionAcceptImpl innerAccept = new PredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(true);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(false);
                });
                filter.and();
            }
            this.accept = predicateEntityExpressionAccept;
        } else if (accept instanceof AggregatePredicateEntityExpressionAccept) {
            AggregatePredicateEntityExpressionAccept aggregatePredicateEntityExpressionAccept = (AggregatePredicateEntityExpressionAccept) accept;
            boolean nextIsOr = aggregatePredicateEntityExpressionAccept.nextIsOr();
            AggregateFilter aggregateFilter = aggregatePredicateEntityExpressionAccept.getAggregateFilter();
            if(nextIsOr){
                aggregateFilter.or(f -> {
                    AggregatePredicateEntityExpressionAcceptImpl innerAccept = new AggregatePredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(true);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(true);
                });
                aggregateFilter.or();
            }else{
                aggregateFilter.and(f -> {
                    AggregatePredicateEntityExpressionAcceptImpl innerAccept = new AggregatePredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(true);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(false);
                });
                aggregateFilter.and();
            }
            this.accept = aggregatePredicateEntityExpressionAccept;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void _whereAnd(SQLActionExpression sqlActionExpression) {
        if (accept instanceof PredicateEntityExpressionAccept) {
            PredicateEntityExpressionAccept predicateEntityExpressionAccept = (PredicateEntityExpressionAccept) accept;
            boolean nextIsOr = predicateEntityExpressionAccept.nextIsOr();
            Filter filter = predicateEntityExpressionAccept.getFilter();
            if (nextIsOr) {
                filter.or(f -> {
                    PredicateEntityExpressionAcceptImpl innerAccept = new PredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(false);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(true);
                });
                filter.or();
            }else{
                filter.and(f -> {
                    PredicateEntityExpressionAcceptImpl innerAccept = new PredicateEntityExpressionAcceptImpl(f);
                    innerAccept.nextOr(false);
                    this.accept = innerAccept;
                    sqlActionExpression.apply();
                    innerAccept.nextOr(false);
                });
                filter.and();
            }
            this.accept = predicateEntityExpressionAccept;
        } else if (accept instanceof AggregatePredicateEntityExpressionAccept) {
            AggregatePredicateEntityExpressionAccept aggregatePredicateEntityExpressionAccept = (AggregatePredicateEntityExpressionAccept) accept;
            boolean nextIsOr = aggregatePredicateEntityExpressionAccept.nextIsOr();
            AggregateFilter aggregateFilter = aggregatePredicateEntityExpressionAccept.getAggregateFilter();
            if(nextIsOr){
                aggregateFilter.or(f -> {
                    AggregatePredicateEntityExpressionAcceptImpl innerAccept = new AggregatePredicateEntityExpressionAcceptImpl(f);
                    this.accept = innerAccept;
                    innerAccept.nextOr(false);
                    sqlActionExpression.apply();
                    innerAccept.nextOr(true);
                });
                aggregateFilter.or();
            }else{
                aggregateFilter.and(f -> {
                    AggregatePredicateEntityExpressionAcceptImpl innerAccept = new AggregatePredicateEntityExpressionAcceptImpl(f);
                    this.accept = innerAccept;
                    innerAccept.nextOr(false);
                    sqlActionExpression.apply();
                    innerAccept.nextOr(false);
                });
                aggregateFilter.and();
            }
            this.accept = aggregatePredicateEntityExpressionAccept;
        } else {
            throw new UnsupportedOperationException();
        }
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
