package com.easy.query.api.proxy.select.extension.queryable4;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.MultiProxyFilter5;
import com.easy.query.api.proxy.select.extension.queryable5.sql.impl.MultiProxyFilter5Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientProxyQueryable4Available<T1, T2, T3, T4>, ProxyQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinProxy
     * @param on
     * @param <T5Proxy>
     * @param <T5>
     * @return
     */

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoin(T5Proxy joinProxy, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy.create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy, entityQueryable5);
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoin(ProxyQueryable<T5Proxy, T5> joinQueryable, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoin(T5Proxy joinProxy, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy.create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy, entityQueryable5);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoin(ProxyQueryable<T5Proxy, T5> joinQueryable, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoin(T5Proxy joinProxy, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy.create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinProxy, entityQueryable5);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> ProxyQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoin(ProxyQueryable<T5Proxy, T5> joinQueryable, SQLExpression1<MultiProxyFilter5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            on.apply(new MultiProxyFilter5Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(),getRuntimeContext())));
        });
        return new EasyProxyQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);

    }
}
