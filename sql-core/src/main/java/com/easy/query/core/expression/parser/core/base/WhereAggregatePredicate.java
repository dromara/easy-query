package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.AggregateFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/2/18 22:17
 *
 * @author xuejiaming
 */
public interface WhereAggregatePredicate<T1> extends EntitySQLTableOwner<T1>, SQLPropertyNative<WhereAggregatePredicate<T1>>, SQLFxAvailable {
    AggregateFilter getAggregateFilter();

    default QueryRuntimeContext getRuntimeContext() {
        return getAggregateFilter().getRuntimeContext();
    }


    <T2> WhereAggregatePredicate<T2> then(WhereAggregatePredicate<T2> sub);

    default WhereAggregatePredicate<T1> and() {
        return and(true);
    }

    WhereAggregatePredicate<T1> and(boolean condition);

    default WhereAggregatePredicate<T1> and(SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return and(true, sqlAggregatePredicateSQLExpression);
    }

    WhereAggregatePredicate<T1> and(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

    default WhereAggregatePredicate<T1> or() {
        return or(true);
    }

    WhereAggregatePredicate<T1> or(boolean condition);

    default WhereAggregatePredicate<T1> or(SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression) {
        return or(true, sqlAggregatePredicateSQLExpression);
    }

    WhereAggregatePredicate<T1> or(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> sqlAggregatePredicateSQLExpression);

}
