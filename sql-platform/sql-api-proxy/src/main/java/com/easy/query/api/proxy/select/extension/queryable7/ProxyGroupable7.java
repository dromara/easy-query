package com.easy.query.api.proxy.select.extension.queryable7;

import com.easy.query.api.proxy.select.ProxyQueryable7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.MultiProxyGroupSelector7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.impl.MultiProxyGroupSelector7Impl;
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
public interface ProxyGroupable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientProxyQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, ProxyQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> groupBy(SQLGroupSelect... propColumns) {
        return groupBy(true, propColumns);
    }
    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> groupBy(boolean condition, SQLGroupSelect... propColumns) {
        if(condition){
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLGroupSelect propColumn : propColumns) {
                    getClientQueryable7().groupBy(groupBySelector -> {
                        propColumn.accept(groupBySelector.getGroupSelector());
                    });
                }
            }
        }
        return getQueryable7();
    }


    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> groupBy(SQLExpression1<MultiProxyGroupSelector7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable7().groupBy((t, t1, t2, t3, t4, t5, t6) -> {
                selectExpression.apply(new MultiProxyGroupSelector7Impl<>(t.getGroupSelector(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy()));
            });
        }
        return getQueryable7();
    }
}
