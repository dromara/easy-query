package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnTrueOrFalsePredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativeLazyPredicateImpl;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativePredicateImpl;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativesPredicateImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLLazyFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/23 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class AggregateFilterImpl implements AggregateFilter {
    private final QueryRuntimeContext runtimeContext;
    protected final PredicateSegment rootPredicateSegment;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final ExpressionContext expressionContext;
    protected PredicateSegment nextPredicateSegment;

    public AggregateFilterImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, PredicateSegment predicateSegment) {
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;

        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        this.expressionContext = entityQueryExpressionBuilder.getExpressionContext();
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return this.runtimeContext;
    }

    @Override
    public AggregateFilter func0(TableAvailable table, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val) {
        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(table, columnFunction, property, val, compare, runtimeContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Object val) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlNativeExpressionContext.value(val);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment + " " + compare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter func(TableAvailable table1, SQLFunction sqlFunction1, SQLPredicateCompare compare, TableAvailable table2,SQLFunction sqlFunction2) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContextLeft = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction1.consume(new SQLNativeChainExpressionContextImpl(table1, sqlNativeExpressionContextLeft));
        String sqlSegmentLeft = sqlFunction1.sqlSegment(table1);
        SQLNativePredicateImpl sqlNativePredicateLeft = new SQLNativePredicateImpl(runtimeContext, sqlSegmentLeft, sqlNativeExpressionContextLeft);
        SQLNativeExpressionContextImpl sqlNativeExpressionContextRight = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction2.consume(new SQLNativeChainExpressionContextImpl(table2, sqlNativeExpressionContextRight));
        String sqlSegmentRight = sqlFunction2.sqlSegment(table2);
        SQLNativePredicateImpl sqlNativePredicateRight = new SQLNativePredicateImpl(runtimeContext, sqlSegmentRight, sqlNativeExpressionContextRight);

        nextPredicateSegment.setPredicate(new SQLNativesPredicateImpl(runtimeContext, sqlNativePredicateLeft, compare, sqlNativePredicateRight));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, TableAvailable table2, String property) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlNativeExpressionContext.expression(table2, property);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment + " " + compare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare sqlPredicateAssert) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(),runtimeContext);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table,sqlNativeExpressionContext));
        String sqlSegment = sqlFunction.sqlSegment(table);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment+" "+sqlPredicateAssert.getSQL(), sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public <TProperty> AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Query<TProperty> subQuery) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlNativeExpressionContext.expression(subQuery);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment + " " + compare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public <TProperty> AggregateFilter func(TableAvailable table, SQLFunction sqlFunction, SQLPredicateCompare compare, Collection<TProperty> collections) {

        if (EasyCollectionUtil.isEmpty(collections)) {
            if (SQLPredicateCompareEnum.IN == compare) {
                nextPredicateSegment.setPredicate(new ColumnTrueOrFalsePredicate(false, compare, table));
                nextAnd();
                return this;
            } else if (SQLPredicateCompareEnum.NOT_IN == compare) {
                nextPredicateSegment.setPredicate(new ColumnTrueOrFalsePredicate(true, compare, table));
                nextAnd();
                return this;
            } else {
                throw new UnsupportedOperationException();
            }
        }
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        sqlFunction.consume(new SQLNativeChainExpressionContextImpl(table, sqlNativeExpressionContext));
        String sqlSegment = sqlFunction.sqlSegment(table);
        sqlNativeExpressionContext.collection(collections);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment + " " + compare.getSQL() + " {" + sqlFunction.paramMarks() + "}", sqlNativeExpressionContext));
        nextAnd();
        return this;
    }


    @Override
    public AggregateFilter like(TableAvailable leftTable, String property1, Object val, boolean like, SQLLikeEnum sqlLike) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.column(leftTable, property1)
                    .value(val);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction, Object val, boolean like, SQLLikeEnum sqlLike) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.sqlFunc(leftTable, sqlFunction)
                    .value(val);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public AggregateFilter like(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2, boolean like, SQLLikeEnum sqlLike) {
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.column(leftTable, property1)
                    .column(rightTable, property2);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public AggregateFilter like(TableAvailable leftTable, String property1, TableAvailable rightTable, SQLFunction sqlFunction, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.column(leftTable, property1)
                    .sqlFunc(rightTable, sqlFunction);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction, TableAvailable rightTable, String property2, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.sqlFunc(leftTable, sqlFunction)
                    .column(rightTable, property2);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    @Override
    public AggregateFilter like(TableAvailable leftTable, SQLFunction sqlFunction1, TableAvailable rightTable, SQLFunction sqlFunction2, boolean like, SQLLikeEnum sqlLike) {

        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(expressionContext, runtimeContext);
        SQLFunction likeSQLFunction = runtimeContext.fx().like(x -> {
            x.sqlFunc(leftTable, sqlFunction1)
                    .sqlFunc(rightTable, sqlFunction2);
        },like,sqlLike);
        return getLikePredicateFilter(leftTable, sqlNativeExpressionContext, likeSQLFunction);
    }

    private AggregateFilter getLikePredicateFilter(TableAvailable leftTable, SQLNativeExpressionContextImpl sqlNativeExpressionContext, SQLFunction likeSQLFunction) {
        likeSQLFunction.consume(new SQLNativeChainExpressionContextImpl(leftTable, sqlNativeExpressionContext));
        if (likeSQLFunction instanceof SQLLazyFunction) {
            SQLLazyFunction sqlLazyFunction = (SQLLazyFunction) likeSQLFunction;
            nextPredicateSegment.setPredicate(new SQLNativeLazyPredicateImpl(runtimeContext, expressionContext, sqlLazyFunction, sqlSegment -> sqlSegment, sqlNativeExpressionContext));
        } else {
            String sqlSegment = likeSQLFunction.sqlSegment(leftTable);
            nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment, sqlNativeExpressionContext));
        }
        nextAnd();
        return this;
    }




    @Override
    public AggregateFilter sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(), runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        nextPredicateSegment.setPredicate(new SQLNativePredicateImpl(runtimeContext, sqlSegment, sqlNativeExpressionContext));
        nextAnd();
        return this;
    }

    @Override
    public AggregateFilter and() {
        this.nextPredicateSegment = new AndPredicateSegment();
        return this;
    }

    @Override
    public AggregateFilter and(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression) {
        this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
        AggregateFilterImpl aggregateFilter = new AggregateFilterImpl(entityQueryExpressionBuilder, this.nextPredicateSegment);
        aggregateFilterSQLExpression.apply(aggregateFilter);
        return this;
    }

    @Override
    public AggregateFilter or() {
        this.nextPredicateSegment = new OrPredicateSegment();
        return this;
    }

    @Override
    public AggregateFilter or(SQLExpression1<AggregateFilter> aggregateFilterSQLExpression) {
        this.nextPredicateSegment = new OrPredicateSegment();
        this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
        AggregateFilterImpl aggregateFilter = new AggregateFilterImpl(entityQueryExpressionBuilder, this.nextPredicateSegment);
        aggregateFilterSQLExpression.apply(aggregateFilter);
        return this;
    }
}
