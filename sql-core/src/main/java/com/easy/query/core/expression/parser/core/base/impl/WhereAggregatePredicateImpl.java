package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/18 22:41
 */
public class WhereAggregatePredicateImpl<T1> implements WhereAggregatePredicate<T1> {
    protected final TableAvailable table;
    private final AggregateFilter aggregateFilter;

    public WhereAggregatePredicateImpl(TableAvailable table, AggregateFilter aggregateFilter) {
        this.table = table;
        this.aggregateFilter = aggregateFilter;
    }

    @Override
    public AggregateFilter getAggregateFilter() {
        return aggregateFilter;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public WhereAggregatePredicate<T1> func(boolean condition, ColumnFunction columnFunction, String property, SQLPredicateCompare compare, Object val) {
        if (condition) {
            aggregateFilter.func0(table,columnFunction,property,compare,val);
        }
        return this;
    }

    @Override
    public <T2> WhereAggregatePredicate<T2> then(WhereAggregatePredicate<T2> sub) {
        return sub;
    }

    @Override
    public WhereAggregatePredicate<T1> and(boolean condition) {
        if (condition) {
            aggregateFilter.and();
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> and(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            aggregateFilter.and(f->{
                sqlAggregatePredicateSQLExpression.apply(new WhereAggregatePredicateImpl<>(table,aggregateFilter));
            });
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> or(boolean condition) {
        if (condition) {
            aggregateFilter.or();
        }
        return this;
    }

    @Override
    public WhereAggregatePredicate<T1> or(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        if (condition) {
            aggregateFilter.or(f->{
                sqlAggregatePredicateSQLExpression.apply(new WhereAggregatePredicateImpl<>(table,aggregateFilter));
            });
        }
        return this;
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(aggregateFilter);
    }

    @Override
    public WhereAggregatePredicate<T1> castChain() {
        return this;
    }
}
