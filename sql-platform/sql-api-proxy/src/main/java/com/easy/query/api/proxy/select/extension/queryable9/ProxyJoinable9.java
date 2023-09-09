package com.easy.query.api.proxy.select.extension.queryable9;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable10;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable10;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.Tuple10;
import com.easy.query.core.expression.lambda.SQLExpression11;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends ClientProxyQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, ProxyQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {


    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(T10Proxy joinProxy, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinProxy.create(t9.getTable()));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(T10Proxy joinProxy, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinProxy.create(t9.getTable()));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(T10Proxy joinProxy, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinProxy.create(t9.getTable()));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression11<ProxyFilter, T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new ProxyFilterImpl(t.getFilter()), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(),  get9Proxy(), joinQueryable.get1Proxy());
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }


    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoinMerge(T10Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return leftJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoinMerge(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return leftJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoinMerge(T10Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return rightJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoinMerge(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return rightJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoinMerge(T10Proxy joinProxy, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return innerJoin(joinProxy, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoinMerge(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression2<ProxyFilter, Tuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return innerJoin(joinQueryable, (f, t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(f, new Tuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}
