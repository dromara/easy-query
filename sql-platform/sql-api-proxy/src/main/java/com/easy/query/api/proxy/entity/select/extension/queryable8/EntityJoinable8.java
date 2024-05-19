package com.easy.query.api.proxy.entity.select.extension.queryable8;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression9;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends ClientEntityQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, EntityQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {


    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(Class<T9> joinClass, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {        
        T9Proxy t9Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t9Proxy,on);
    }
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(T9Proxy t9Proxy, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(t9Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), t9Proxy.create(t8.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), t9Proxy, entityQueryable9);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(Class<T9> joinClass, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        T9Proxy t9Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t9Proxy, on);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(T9Proxy t9Proxy, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(t9Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), t9Proxy.create(t8.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), t9Proxy, entityQueryable9);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(Class<T9> joinClass, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        T9Proxy t9Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t9Proxy,on);
    }
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(T9Proxy t9Proxy, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(t9Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), t9Proxy.create(t8.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), t9Proxy, entityQueryable9);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression9< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }


    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoinMerge(Class<T9> joinClass, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoinMerge(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoinMerge(Class<T9> joinClass, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoinMerge(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9 extends ProxyEntityAvailable<T9,T9Proxy>> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoinMerge(Class<T9> joinClass, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> EntityQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoinMerge(EntityQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MergeTuple9<>(t, t1, t2, t3, t4, t5, t6, t7, t8));
        });
    }

}
