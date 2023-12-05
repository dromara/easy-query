package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityHavingable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy,T1,T2Proxy, T2> {

    default EntityQueryable2<T1Proxy,T1,T2Proxy, T2> having(SQLFuncExpression2<T1Proxy, T2Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        return having(true,predicateExpression);
    }

    default EntityQueryable2<T1Proxy,T1,T2Proxy, T2> having(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, SQLAggregatePredicateExpression> predicateExpression) {
        if (condition) {
            getClientQueryable2().having((predicate1, predicate2) -> {
                SQLAggregatePredicateExpression sqlAggregatePredicate = predicateExpression.apply(get1Proxy(), get2Proxy());
                sqlAggregatePredicate.accept(predicate1.getAggregateFilter());
            });
        }
        return getQueryable2();
    }
    default EntityQueryable2<T1Proxy,T1,T2Proxy, T2> havingMerge(SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>, SQLAggregatePredicateExpression> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default EntityQueryable2<T1Proxy,T1,T2Proxy, T2> havingMerge(boolean condition, SQLFuncExpression1<Tuple2<T1Proxy, T2Proxy>, SQLAggregatePredicateExpression> predicateExpression){
        return having(condition,(t1, t2)->{
            return predicateExpression.apply(new Tuple2<>(t1,t2));
        });
    }

}
