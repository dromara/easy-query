package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.enums.SqlPredicateCompare;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.internal.AggregatePredicate;
import org.easy.query.core.expression.parser.abstraction.SqlAggregatePredicate;
import org.easy.query.core.enums.IEasyFunc;
import org.easy.query.core.expression.segment.condition.AndPredicateSegment;
import org.easy.query.core.expression.segment.condition.OrPredicateSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;
import org.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate0;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;

/**
 * @FileName: DefaultSqlAggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 * @Created by xuejiaming
 */
public class DefaultSqlAggregatePredicate<T1> implements SqlAggregatePredicate<T1> {
    private final int index;
    private final SqlEntityExpression sqlEntityExpression;
    private final PredicateSegment rootPredicateSegment;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlAggregatePredicate(int index, SqlEntityExpression sqlEntityExpression, PredicateSegment predicateSegment) {
        this.index = index;
        this.sqlEntityExpression = sqlEntityExpression;
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }
    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }
    @Override
    public SqlAggregatePredicate<T1> func(boolean condition, IEasyFunc easyAggregate, Property<T1, ?> column, SqlPredicateCompare compare, Object val) {
        if (condition) {
            SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new FuncColumnValuePredicate0(table,easyAggregate, propertyName, val, compare, sqlEntityExpression));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2, TChain2> AggregatePredicate<T2, TChain2> then(AggregatePredicate<T2, TChain2> sub) {
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
            SqlAggregatePredicate<T1> sqlPredicate = sqlEntityExpression.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, sqlEntityExpression, this.nextPredicateSegment);
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
            SqlAggregatePredicate<T1> sqlPredicate = sqlEntityExpression.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, sqlEntityExpression, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
