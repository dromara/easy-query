package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.MergeSelectTuple3;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultClass, SQLFuncExpression4<T1Proxy, T2Proxy, T3Proxy, TRProxy, SQLSelectAsExpression> selectExpression) {
        TRProxy trProxy = EntityQueryProxyManager.create(resultClass);
        ClientQueryable<TR> select = getClientQueryable3().select(resultClass, (selector1, selector2, selector3) -> {
            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), trProxy);
            sqlSelectAs.accept(selector1.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectMerge(Class<TR> resultClass, SQLFuncExpression1<MergeSelectTuple3<T1Proxy, T2Proxy, T3Proxy, TRProxy>, SQLSelectAsExpression> selectExpression) {
        return select(resultClass, (t1, t2, t3, tr) -> {
            return selectExpression.apply(new MergeSelectTuple3<>(t1, t2, t3, tr));
        });
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(TRProxy trProxy, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLSelectAsExpression> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable3().select(trProxy.getEntityClass(), (selector1, selector2, selector3) -> {
            SQLSelectAsExpression sqlSelectAs = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
            sqlSelectAs.accept(selector1.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(TRProxy trProxy, SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, SQLSelectAsExpression> selectExpression) {
        return select(trProxy, (t1, t2, t3) -> {
            return selectExpression.apply(new MergeTuple3<>(t1, t2, t3));
        });
    }
}
