package com.easy.query.api.proxy.select.extension.queryable5;

import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxyGroupSelector5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.impl.MultiProxyGroupSelector5Impl;
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
public interface ProxyGroupable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientProxyQueryable5Available<T1, T2, T3, T4, T5>, ProxyQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(SQLGroupSelect... propColumns) {
        return groupBy(true, propColumns);
    }
    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(boolean condition, SQLGroupSelect... propColumns) {
        if(condition){
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLGroupSelect propColumn : propColumns) {
                    getClientQueryable5().groupBy(groupBySelector -> {
                        propColumn.accept(groupBySelector.getGroupSelector());
                    });
                }
            }
        }
        return getQueryable5();
    }


    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(SQLExpression1<MultiProxyGroupSelector5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable5().groupBy((t, t1, t2, t3, t4) -> {
                selectExpression.apply(new MultiProxyGroupSelector5Impl<>(t.getGroupSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy()));
            });
        }
        return getQueryable5();
    }
}
