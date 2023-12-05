package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyGroupSelector2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyGroupSelector2Impl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1,T2>, ProxyQueryable2Available<T1Proxy,T1,T2Proxy,T2> {

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(SQLGroupByExpression... propColumns) {
        return groupBy(true, propColumns);
    }
    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(boolean condition, SQLGroupByExpression... propColumns) {
        if(condition){
            if(EasyArrayUtil.isNotEmpty(propColumns)){
                for (SQLGroupByExpression propColumn : propColumns) {
                    getClientQueryable2().groupBy(groupBySelector -> {
                        propColumn.accept(groupBySelector.getGroupSelector());
                    });
                }
            }
        }
        return getQueryable2();
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(SQLExpression1<MultiProxyGroupSelector2<T1Proxy, T2Proxy>> selectExpression) {
        return groupBy(true,selectExpression);
    }

    default ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> groupBy(boolean condition, SQLExpression1<MultiProxyGroupSelector2<T1Proxy, T2Proxy>> selectExpression) {
        if (condition) {
            getClientQueryable2().groupBy((selector1, selector2) -> {
                selectExpression.apply(new MultiProxyGroupSelector2Impl<>(selector2.getGroupSelector(), get1Proxy(), get2Proxy()));
            });
        }
        return getQueryable2();
    }
}
