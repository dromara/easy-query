package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAs;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass, SQLFuncExpression3<T1Proxy, T2Proxy, TRProxy, SQLSelectAs> selectExpression) {
        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
        ClientQueryable<TR> select = getClientQueryable2().select(resultEntityClass, (selector1, selector2) -> {
            SQLSelectAs sqlSelectAs = selectExpression.apply(get1Proxy(), get2Proxy(), trProxy);
            sqlSelectAs.accept(selector1.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectMerge(Class<TR> resultEntityClass, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, TRProxy>, SQLSelectAs> selectExpression) {
        return select(resultEntityClass, (t1, t2, tr) -> {
            return selectExpression.apply(new Tuple3<>(t1, t2, tr));
        });
    }
}
