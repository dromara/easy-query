package org.easy.query.core.impl.lambda.select;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.base.AggregatePredicate;
import org.easy.query.core.abstraction.sql.base.SqlAggregatePredicate;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.abstraction.sql.enums.IEasyPredicate;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.segments.AndPredicateSegment;
import org.easy.query.core.segments.OrPredicateSegment;
import org.easy.query.core.segments.PredicateSegment;
import org.easy.query.core.segments.predicate.FuncColumnValuePredicate;

/**
 * @FileName: DefaultSqlAggregatePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/18 22:41
 * @Created by xuejiaming
 */
public class DefaultSqlAggregatePredicate<T1> implements SqlAggregatePredicate<T1> {
    private final int index;
    private final SelectContext selectContext;
    private final PredicateSegment rootPredicateSegment;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlAggregatePredicate(int index, SelectContext selectContext, PredicateSegment predicateSegment) {
        this.index = index;
        this.selectContext = selectContext;
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }
    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }
    @Override
    public SqlAggregatePredicate<T1> func(boolean condition, IEasyFunc easyAggregate, Property<T1, ?> column, IEasyPredicate predicate, Object val) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(index,easyAggregate, columnName, val, predicate, selectContext));
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
            SqlAggregatePredicate<T1> sqlPredicate = selectContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, selectContext, this.nextPredicateSegment);
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
            SqlAggregatePredicate<T1> sqlPredicate = selectContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlAggregatePredicate(index, selectContext, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
