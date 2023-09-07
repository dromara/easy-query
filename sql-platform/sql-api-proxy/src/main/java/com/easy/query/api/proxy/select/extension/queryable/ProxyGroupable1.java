package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyGroupSelector;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {

    default ProxyQueryable<T1Proxy, T1> groupBy(SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression) {
        return groupBy(true, selectExpression);
    }

    ProxyQueryable<T1Proxy, T1> groupBy(boolean condition, SQLExpression2<ProxyGroupSelector, T1Proxy> selectExpression);
}
