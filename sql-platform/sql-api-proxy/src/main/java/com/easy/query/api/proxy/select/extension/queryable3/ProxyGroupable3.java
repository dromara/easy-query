package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyGroupSelector3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyGroupSelector3Impl;
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
public interface ProxyGroupable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLGroupSelect... propColumns) {
        return groupBy(true, propColumns);
    }
    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLGroupSelect... propColumns) {
        if(condition){
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLGroupSelect propColumn : propColumns) {
                    getClientQueryable3().groupBy(groupBySelector -> {
                        propColumn.accept(groupBySelector.getGroupSelector());
                    });
                }
            }
        }
        return getQueryable3();
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(SQLExpression1<MultiProxyGroupSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    default ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector3<T1Proxy, T2Proxy, T3Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable3().groupBy((selector1, selector2, selector3) -> {
                selectExpression.apply(new MultiProxyGroupSelector3Impl<>(selector2.getGroupSelector(), get1Proxy(), get2Proxy(), get3Proxy()));
            });
        }
        return getQueryable3();
    }
}
