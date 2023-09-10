package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyAggregateFilter;
import com.easy.query.api.proxy.sql.impl.ProxyAggregateFilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyHavingable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>,ProxyQueryableAvailable<T1Proxy,T1>{



    default ProxyQueryable<T1Proxy, T1> having(SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression) {
        return having(true, aggregateFilterSQLExpression);
    }

    default ProxyQueryable<T1Proxy, T1> having(boolean condition, SQLExpression2<ProxyAggregateFilter, T1Proxy> aggregateFilterSQLExpression){

        if (condition) {
            getClientQueryable().having(whereAggregatePredicate -> {
                aggregateFilterSQLExpression.apply(new ProxyAggregateFilterImpl(whereAggregatePredicate.getAggregateFilter()), get1Proxy());
            });
        }
        return getQueryable();
    }


}
