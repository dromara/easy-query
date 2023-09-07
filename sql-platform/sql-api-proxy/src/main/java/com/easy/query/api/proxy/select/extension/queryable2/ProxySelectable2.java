package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.impl.ProxyAsSelectorImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.Tuple2;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(TRProxy trProxy, SQLExpression3<ProxyAsSelector<TRProxy, TR>, T1Proxy, T2Proxy> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable2().select(trProxy.getEntityClass(), (selector1, selector2) -> {
            selectExpression.apply(new ProxyAsSelectorImpl<>(trProxy, selector2.getAsSelector()), getQueryable2().get1Proxy(), getQueryable2().get2Proxy());
        });
        return new EasyProxyQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> selectMerge(TRProxy trProxy, SQLExpression2<ProxyAsSelector<TRProxy, TR>, Tuple2<T1Proxy, T2Proxy>> selectExpression) {
        return select(trProxy, (selector, t1, t2) -> {
            selectExpression.apply(selector, new Tuple2<>(t1, t2));
        });
    }
}
