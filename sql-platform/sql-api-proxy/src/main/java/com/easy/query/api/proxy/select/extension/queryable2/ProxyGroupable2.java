package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
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
public interface ProxyGroupable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1,T2>, ProxyQueryable2Available<T1Proxy,T1,T2Proxy,T2> {


    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(SQLExpression3<ProxyGroupSelector, T1Proxy, T2Proxy> selectExpression) {
        return groupBy(true,selectExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(boolean condition, SQLExpression3<ProxyGroupSelector, T1Proxy, T2Proxy> selectExpression) {
        if (condition) {
            getClientQueryable2().groupBy((selector1, selector2) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(selector2.getGroupSelector()), get1Proxy(), get2Proxy());
            });
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupByMerge(SQLExpression2<ProxyGroupSelector,Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector,Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector,t1, t2) -> {
            selectExpression.apply(groupSelector,new Tuple2<>(t1, t2));
        });
    }
}
