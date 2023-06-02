package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 */
public class WhereAggregatePredicateImpl<T1> implements WhereAggregatePredicate<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final QueryRuntimeContext runtimeContext;
    protected final PredicateSegment rootPredicateSegment;
    protected final TableAvailable table;
    protected PredicateSegment nextPredicateSegment;

    public WhereAggregatePredicateImpl(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.runtimeContext = entityExpressionBuilder.getRuntimeContext();
        this.table = entityExpressionBuilder.getTable(index).getEntityTable();
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public WhereAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val) {
        if (condition) {
            nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(), columnFunction, property, val, compare, runtimeContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2> WhereAggregatePredicate<T2> then(WhereAggregatePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return sub;
    }

    @Override
    public WhereAggregatePredicate<T1> and(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> and(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            WhereAggregatePredicate<T1> sqlPredicate = runtimeContext.getSQLExpressionInvokeFactory().createWhereAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlAggregatePredicateSQLExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> or(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> or(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            WhereAggregatePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory().createWhereAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlAggregatePredicateSQLExpression.apply(sqlPredicate);
        }
        return this;
    }
}
