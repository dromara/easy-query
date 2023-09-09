package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable3;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression4;
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

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(T3Proxy joinProxy, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinProxy.create(t2.getTable()));
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinProxy, entityQueryable3);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinQueryable.getProxy());
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinQueryable.getProxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(T3Proxy joinProxy, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinProxy.create(t2.getTable()));
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinProxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinQueryable.getProxy());
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinQueryable.getProxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(T3Proxy joinProxy, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinProxy.getEntityClass(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinProxy.create(t2.getTable()));
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinProxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoin(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression4<ProxyFilter, T1Proxy, T2Proxy, T3Proxy> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), getProxy(), get2Proxy(), joinQueryable.getProxy());
        });
        return new EasyProxyQueryable3<>(getProxy(), get2Proxy(), joinQueryable.getProxy(), entityQueryable3);

    }


    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoinMerge(T3Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return leftJoin(joinProxy, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoinMerge(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return leftJoin(joinQueryable, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoinMerge(T3Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return rightJoin(joinProxy, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> rightJoinMerge(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return rightJoin(joinQueryable, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoinMerge(T3Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return innerJoin(joinProxy, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> innerJoinMerge(ProxyQueryable<T3Proxy, T3> joinQueryable, SQLExpression2<ProxyFilter, Tuple3<T1Proxy, T2Proxy, T3Proxy>> on) {
        return innerJoin(joinQueryable, (f, t1, t2, t3) -> {
            on.apply(f, new Tuple3<>(t1, t2, t3));
        });
    }

}
