package com.easy.query.api.proxy.entity.select.extension.queryable5;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression6;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable5<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> extends ClientEntityQueryable5Available<T1, T2, T3, T4,T5>, EntityQueryable5Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> {


    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoin(Class<T6> joinClass, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        T6Proxy t6Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t6Proxy,on);
    }
    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoin(T6Proxy t6Proxy, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(t6Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), t6Proxy.create(t5.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), t6Proxy, entityQueryable6);
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoin(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), joinQueryable.get1Proxy().create(t5.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoin(Class<T6> joinClass, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        T6Proxy t6Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t6Proxy,on);

    }
    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoin(T6Proxy t6Proxy, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(t6Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), t6Proxy.create(t5.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), t6Proxy, entityQueryable6);

    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoin(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), joinQueryable.get1Proxy().create(t5.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoin(Class<T6> joinClass, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        T6Proxy t6Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t6Proxy,on);
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoin(T6Proxy t6Proxy, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(t6Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), t6Proxy.create(t5.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), t6Proxy, entityQueryable6);
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoin(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression6< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy> on) {
        ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6 = getClientQueryable5().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), joinQueryable.get1Proxy().create(t5.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), joinQueryable.get1Proxy(), entityQueryable6);
    }


    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoinMerge(Class<T6> joinClass, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> leftJoinMerge(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoinMerge(Class<T6> joinClass, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> rightJoinMerge(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6 extends ProxyEntityAvailable<T6,T6Proxy>> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoinMerge(Class<T6> joinClass, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

    default <T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> EntityQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> innerJoinMerge(EntityQueryable<T6Proxy, T6> joinQueryable, SQLExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5) -> {
            on.apply(new MergeTuple6<>(t, t1, t2, t3, t4, t5));
        });
    }

}
