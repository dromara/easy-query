package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy,T1,T2Proxy, T2> {

    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> having(SQLExpression3<ProxyAggregateFilter, T1Proxy, T2Proxy> predicateExpression) {
        return having(true,predicateExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> having(boolean condition, SQLExpression3<ProxyAggregateFilter, T1Proxy, T2Proxy> predicateExpression) {
        if (condition) {
            getClientQueryable2().having((predicate1, predicate2) -> {
                predicateExpression.apply(new ProxyAggregateFilterImpl(predicate1.getAggregateFilter()), get1Proxy(), get2Proxy());
            });
        }
        return getQueryable2();
    }
    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> havingMerge(SQLExpression2<ProxyAggregateFilter,Tuple2<T1Proxy, T2Proxy>> predicateExpression) {
        return havingMerge(true, predicateExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy, T2> havingMerge(boolean condition, SQLExpression2<ProxyAggregateFilter,Tuple2<T1Proxy, T2Proxy>> predicateExpression){
        return having(condition,(filter,t1, t2)->{
            predicateExpression.apply(filter,new Tuple2<>(t1, t2));
        });
    }

}
