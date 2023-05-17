package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLAggregatePredicate;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.util.LambdaUtil;

/**
 * @author xuejiaming
 * @FileName: DefaultSqlAggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 */
public class DefaultSQLAggregatePredicate<T1> implements SQLAggregatePredicate<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final EasyQueryRuntimeContext runtimeContext;
    protected final PredicateSegment rootPredicateSegment;
    protected final TableAvailable table;
    protected PredicateSegment nextPredicateSegment;

    public DefaultSQLAggregatePredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
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
    public EasyQueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public SQLAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, Property<T1, ?> column, SQLPredicateCompare compare, Object val) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(), columnFunction, propertyName, val, compare, runtimeContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2> SQLAggregatePredicate<T2> then(SQLAggregatePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return sub;
    }

    @Override
    public SQLAggregatePredicate<T1> and(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return this;
    }

    @Override
    public SQLAggregatePredicate<T1> and(boolean condition, SQLExpression<SQLAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SQLAggregatePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSQLAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlAggregatePredicateSQLExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public SQLAggregatePredicate<T1> or(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
        return this;
    }

    @Override
    public SQLAggregatePredicate<T1> or(boolean condition, SQLExpression<SQLAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SQLAggregatePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSQLAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlAggregatePredicateSQLExpression.apply(sqlPredicate);
        }
        return this;
    }
}
