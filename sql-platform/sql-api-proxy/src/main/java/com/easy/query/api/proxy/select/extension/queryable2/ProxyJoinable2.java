package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.MultiProxyFilter3;
import com.easy.query.api.proxy.select.extension.queryable3.sql.impl.MultiProxyFilter3Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {


    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinProxy 和哪张表进行join
     * @param on        条件
     * @param <T3>
     * @return 返回可查询的表达式支持3表参数
     */

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(T3Proxy joinProxy, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinProxy.create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinProxy, entityQueryable3);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(T3Proxy joinProxy, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinProxy.create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinProxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(T3Proxy joinProxy, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinProxy.create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinProxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression1<MultiProxyFilter3<T1Proxy, T2Proxy, T3Proxy>> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new MultiProxyFilter3Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), joinQueryable.get1Proxy().create(t2.getTable(),getClientQueryable2().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }
}
