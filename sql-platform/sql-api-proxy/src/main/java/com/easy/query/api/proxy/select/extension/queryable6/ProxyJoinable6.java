package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable7;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable7;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.Tuple7;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression8;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4,T5,T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {


    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(T7Proxy joinProxy, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable()));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(T7Proxy joinProxy, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable()));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(T7Proxy joinProxy, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable()));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression8<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }


    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoinMerge(T7Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return leftJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoinMerge(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return leftJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoinMerge(T7Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return rightJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoinMerge(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return rightJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoinMerge(T7Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return innerJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoinMerge(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression2<ProxyFilter, Tuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return innerJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(f, new Tuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

}
