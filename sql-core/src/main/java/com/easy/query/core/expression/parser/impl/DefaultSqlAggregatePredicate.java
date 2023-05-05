package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.enums.EasyFunc;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlAggregatePredicate;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqlAggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 * @author xuejiaming
 */
public class DefaultSqlAggregatePredicate<T1> implements SqlAggregatePredicate<T1> {
    protected final int index;
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final PredicateSegment rootPredicateSegment;
    protected final TableAvailable table;
    protected PredicateSegment nextPredicateSegment;

    public DefaultSqlAggregatePredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
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
    public SqlAggregatePredicate<T1> func(boolean condition, EasyFunc easyAggregate, Property<T1, ?> column, SqlPredicateCompare compare, Object val) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(),easyAggregate, propertyName, val, compare, entityExpressionBuilder.getRuntimeContext()));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2> SqlAggregatePredicate<T2> then(SqlAggregatePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return sub;
    }

    @Override
    public SqlAggregatePredicate<T1> and(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlAggregatePredicate<T1> and(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlAggregatePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public SqlAggregatePredicate<T1> or(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlAggregatePredicate<T1> or(boolean condition, SqlExpression<SqlAggregatePredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlAggregatePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }
}
