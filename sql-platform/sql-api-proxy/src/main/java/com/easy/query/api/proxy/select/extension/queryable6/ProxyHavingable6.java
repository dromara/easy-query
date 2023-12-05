package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.MultiProxyAggregateFilter6;
import com.easy.query.api.proxy.select.extension.queryable6.sql.impl.MultiProxyAggregateFilter6Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicateExpression;
import com.easy.query.core.proxy.sql.Predicate;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(SQLAggregatePredicateExpression... sqlAggregatePredicates) {
        return having(true, sqlAggregatePredicates);
    }
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(boolean condition, SQLAggregatePredicateExpression... sqlAggregatePredicates) {

        if (condition) {
            if(EasyArrayUtil.isNotEmpty(sqlAggregatePredicates)){
                SQLAggregatePredicateExpression sqlAggregatePredicate = Predicate.and(sqlAggregatePredicates);
                getClientQueryable6().having(whereAggregatePredicate -> {
                    sqlAggregatePredicate.accept(whereAggregatePredicate.getAggregateFilter());
                });
            }
        }
        return getQueryable6();
    }
    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(SQLExpression1<MultiProxyAggregateFilter6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> having(boolean condition, SQLExpression1<MultiProxyAggregateFilter6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> predicateExpression) {
        if (condition) {
            getClientQueryable6().having((t, t1, t2, t3, t4,t5) -> {
                predicateExpression.apply(new MultiProxyAggregateFilter6Impl<>(t.getAggregateFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy()));
            });
        }
        return getQueryable6();
    }

}
