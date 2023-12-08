package com.easy.query.api.proxy.select.extension.queryable8;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable9;
import com.easy.query.api.proxy.select.extension.queryable9.sql.MultiProxyFilter9;
import com.easy.query.api.proxy.select.extension.queryable9.sql.impl.MultiProxyFilter9Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends ClientProxyQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, ProxyQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }
}
