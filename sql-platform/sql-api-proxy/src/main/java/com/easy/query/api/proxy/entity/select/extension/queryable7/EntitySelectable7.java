package com.easy.query.api.proxy.entity.select.extension.queryable7;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.tuple.MergeSelectTuple7;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression7;
import com.easy.query.core.expression.lambda.SQLFuncExpression8;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientEntityQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, EntityQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass, SQLFuncExpression8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, TRProxy, SQLSelectAsExpression> selectExpression) {
        TRProxy trProxy = EntityQueryProxyManager.create(resultEntityClass);
        ClientQueryable<TR> select = getClientQueryable7().select(resultEntityClass, (t, t1, t2, t3, t4, t5, t6) -> {
            SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), trProxy);
            sqlSelectAsExpression.accept(t.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectMerge(Class<TR> resultEntityClass, SQLFuncExpression1<MergeSelectTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, TRProxy>, SQLSelectAsExpression> selectExpression) {
        return select(resultEntityClass, (t, t1, t2, t3, t4, t5, t6, tr) -> {
            return selectExpression.apply(new MergeSelectTuple7<>(t, t1, t2, t3, t4, t5, t6, tr));
        });
    }
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(TRProxy trProxy, SQLFuncExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, SQLSelectAsExpression> selectExpression) {
        ClientQueryable<TR> select = getClientQueryable7().select(trProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy());
            sqlSelectAsExpression.accept(t.getAsSelector());
        });
        return new EasyEntityQueryable<>(trProxy, select);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy>> EntityQueryable<TRProxy, TR> selectMerge(TRProxy trProxy, SQLFuncExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>, SQLSelectAsExpression> selectExpression) {
        return select(trProxy, (t, t1, t2, t3, t4, t5, t6) -> {
            return selectExpression.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }
}
