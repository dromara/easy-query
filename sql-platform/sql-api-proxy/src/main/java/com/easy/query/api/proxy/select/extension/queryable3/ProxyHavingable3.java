package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(SQLExpression4<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy> predicateExpression) {
        return having(true, predicateExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> having(boolean condition, SQLExpression4<ProxyAggregateFilter, T1Proxy, T2Proxy, T3Proxy> predicateExpression) {
        if (condition) {
            getClientQueryable3().having((predicate1, predicate2, predicate3) -> {
                predicateExpression.apply(new ProxyAggregateFilterImpl(predicate1.getAggregateFilter()), getProxy(), get2Proxy(), get3Proxy());
            });
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> havingMerge(SQLExpression2<ProxyAggregateFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> havingMerge(boolean condition, SQLExpression2<ProxyAggregateFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> predicateExpression) {
        return having(condition, (filter, t1, t2, t3) -> {
            predicateExpression.apply(filter, new Tuple3<>(t1, t2, t3));
        });
    }

}
