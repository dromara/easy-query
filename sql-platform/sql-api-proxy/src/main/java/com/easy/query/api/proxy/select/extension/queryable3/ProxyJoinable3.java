package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable4;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression5;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinProxy 和哪张表进行join
     * @param on        条件
     * @param <T4>
     * @return 返回可查询的表达式支持3表参数
     */

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy.create(t3.getTable()));
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinProxy, entityQueryable4);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(),joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);

    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy.create(t3.getTable()));
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinProxy, entityQueryable4);

    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(),joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);

    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(T4Proxy joinProxy, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), joinProxy.create(t3.getTable()));
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinProxy, entityQueryable4);

    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression5<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(),joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);

    }


    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoinMerge(T4Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return leftJoin(joinProxy, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoinMerge(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return leftJoin(joinQueryable, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoinMerge(T4Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return rightJoin(joinProxy, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoinMerge(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return rightJoin(joinQueryable, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoinMerge(T4Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return innerJoin(joinProxy, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> ProxyQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoinMerge(ProxyQueryable<T4Proxy, T4> joinQueryable, SQLExpression2<ProxyFilter, Tuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return innerJoin(joinQueryable, (f, t1, t2, t3,t4) -> {
            on.apply(f, new Tuple4<>(t1, t2, t3,t4));
        });
    }

}
