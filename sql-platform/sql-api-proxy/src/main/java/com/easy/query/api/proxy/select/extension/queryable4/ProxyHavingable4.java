package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyAggregateFilter4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxyAggregateFilter4Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLAggregatePredicate;
import com.easy.query.core.proxy.sql.Predicate;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLAggregatePredicate... sqlAggregatePredicates) {
        return having(true, sqlAggregatePredicates);
    }
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition,SQLAggregatePredicate... sqlAggregatePredicates) {

        if (condition) {
            if(EasyArrayUtil.isNotEmpty(sqlAggregatePredicates)){
                SQLAggregatePredicate sqlAggregatePredicate = Predicate.and(sqlAggregatePredicates);
                getClientQueryable4().having(whereAggregatePredicate -> {
                    sqlAggregatePredicate.accept(whereAggregatePredicate.getAggregateFilter());
                });
            }
        }
        return getQueryable4();
    }
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(SQLExpression1<MultiProxyAggregateFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> having(boolean condition, SQLExpression1<MultiProxyAggregateFilter4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> predicateExpression) {
        if (condition) {
            getClientQueryable4().having((t, t1, t2,t3) -> {
                predicateExpression.apply(new MultiProxyAggregateFilter4Impl<>(t.getAggregateFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));
            });
        }
        return getQueryable4();
    }
}
