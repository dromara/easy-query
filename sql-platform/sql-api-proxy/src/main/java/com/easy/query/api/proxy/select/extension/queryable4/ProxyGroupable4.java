package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.MultiProxyGroupSelector4;
import com.easy.query.api.proxy.select.extension.queryable4.sql.impl.MultiProxyGroupSelector4Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupSelect;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(SQLGroupSelect... propColumns) {
        return groupBy(true, propColumns);
    }
    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLGroupSelect... propColumns) {
        if(condition){
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLGroupSelect propColumn : propColumns) {
                    getClientQueryable4().groupBy(groupBySelector -> {
                        propColumn.accept(groupBySelector.getGroupSelector());
                    });
                }
            }
        }
        return getQueryable4();
    }


    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(SQLExpression1<MultiProxyGroupSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector4<T1Proxy, T2Proxy, T3Proxy, T4Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable4().groupBy((t, t1, t2,t3) -> {
                selectExpression.apply(new MultiProxyGroupSelector4Impl<>(t.getGroupSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy()));
            });
        }
        return getQueryable4();
    }
}
