package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.api.proxy.sql.impl.ProxyGroupSelectorImpl;
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
public interface ProxyGroupable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLExpression4<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression4<ProxyGroupSelector, T1Proxy, T2Proxy, T3Proxy> selectExpression) {
        if (condition) {
            getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
                selectExpression.apply(new ProxyGroupSelectorImpl(selector2.getGroupSelector()), get1Proxy(), get2Proxy(), get3Proxy());
            });
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByMerge(SQLExpression2<ProxyGroupSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return groupByMerge(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupByMerge(boolean condition, SQLExpression2<ProxyGroupSelector, Tuple3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return groupBy(condition, (groupSelector, t1, t2, t3) -> {
            selectExpression.apply(groupSelector, new Tuple3<>(t1, t2, t3));
        });
    }
}
