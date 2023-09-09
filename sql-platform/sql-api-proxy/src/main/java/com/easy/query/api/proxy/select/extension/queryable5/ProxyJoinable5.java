package com.easy.query.api.proxy.select.extension.queryable5;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable6;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.Tuple6;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression7;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientProxyQueryable5Available<T1, T2, T3, T4,T5>, ProxyQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {


    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoin(T6Proxy joinProxy, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy.create(t5.getTable()));
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy, entityQueryable6);
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoin(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoin(T6Proxy joinProxy, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy.create(t5.getTable()));
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy, entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoin(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoin(T6Proxy joinProxy, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy.create(t5.getTable()));
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinProxy, entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoin(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression7<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);

    }


    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoinMerge(T6Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return leftJoin(joinProxy, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoinMerge(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return leftJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoinMerge(T6Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return rightJoin(joinProxy, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoinMerge(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return rightJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoinMerge(T6Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return innerJoin(joinProxy, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoinMerge(ProxyQueryable<T6Proxy, T6> joinQueryable, SQLExpression2<ProxyFilter, Tuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return innerJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5) -> {
            on.apply(f, new Tuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

}
