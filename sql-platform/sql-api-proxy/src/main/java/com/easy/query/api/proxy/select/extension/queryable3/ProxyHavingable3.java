package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyAggregateFilter3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyAggregateFilter3Impl;
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
public interface ProxyHavingable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLAggregatePredicateExpression... sqlAggregatePredicates) {
        return having(true, sqlAggregatePredicates);
    }
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLAggregatePredicateExpression... sqlAggregatePredicates) {

        if (condition) {
            if(EasyArrayUtil.isNotEmpty(sqlAggregatePredicates)){
                SQLAggregatePredicateExpression sqlAggregatePredicate = Predicate.and(sqlAggregatePredicates);
                getClientQueryable3().having(whereAggregatePredicate -> {
                    sqlAggregatePredicate.accept(whereAggregatePredicate.getAggregateFilter());
                });
            }
        }
        return getQueryable3();
    }
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLExpression1<MultiProxyAggregateFilter3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression1<MultiProxyAggregateFilter3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        if (condition) {
            getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
                predicateExpression.apply(new MultiProxyAggregateFilter3Impl<>(predicate1.getAggregateFilter(), get1Proxy(), get2Proxy(), get3Proxy()));
            });
        }
        return getQueryable3();
    }
}
