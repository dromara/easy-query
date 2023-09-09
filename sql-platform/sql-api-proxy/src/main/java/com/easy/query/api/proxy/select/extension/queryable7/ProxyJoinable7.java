package com.easy.query.api.proxy.select.extension.queryable7;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable8;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable8;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.Tuple8;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression9;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientProxyQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, ProxyQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {


    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoin(T8Proxy joinProxy, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy.create(t7.getTable()));
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy, entityQueryable8);
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoin(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);

    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoin(T8Proxy joinProxy, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy.create(t7.getTable()));
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy, entityQueryable8);

    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoin(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);

    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoin(T8Proxy joinProxy, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy.create(t7.getTable()));
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinProxy, entityQueryable8);

    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoin(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression9<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);

    }


    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoinMerge(T8Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return leftJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoinMerge(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return leftJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoinMerge(T8Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return rightJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoinMerge(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return rightJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoinMerge(T8Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return innerJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoinMerge(ProxyQueryable<T8Proxy, T8> joinQueryable, SQLExpression2<ProxyFilter, Tuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return innerJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(f, new Tuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

}
