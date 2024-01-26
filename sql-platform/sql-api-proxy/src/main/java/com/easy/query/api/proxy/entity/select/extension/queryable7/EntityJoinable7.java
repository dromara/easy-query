package com.easy.query.api.proxy.entity.select.extension.queryable7;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable8;
import com.easy.query.core.common.tuple.MergeTuple8;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression8;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable7<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> extends ClientEntityQueryable7Available<T1, T2, T3, T4, T5, T6, T7>, EntityQueryable7Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> {


    default <T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoin(Class<T8> joinClass, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        T8Proxy t8Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().leftJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy.create(t7.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy, entityQueryable8);
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoin(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy().create(t7.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);

    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoin(Class<T8> joinClass, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        T8Proxy t8Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy.create(t7.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy, entityQueryable8);

    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoin(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy().create(t7.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);

    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoin(Class<T8> joinClass, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        T8Proxy t8Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy.create(t7.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), t8Proxy, entityQueryable8);

    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoin(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression8< T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy> on) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8 = getClientQueryable7().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy().create(t7.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable8<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), joinQueryable.get1Proxy(), entityQueryable8);
    }


    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoinMerge(Class<T8> joinClass, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return leftJoin(joinClass, ( t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> leftJoinMerge(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoinMerge(Class<T8> joinClass, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> rightJoinMerge(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8  extends ProxyEntityAvailable<T8,T8Proxy>> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoinMerge(Class<T8> joinClass, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

    default<T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> EntityQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> innerJoinMerge(EntityQueryable<T8Proxy, T8> joinQueryable, SQLExpression1<MergeTuple8<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            on.apply(new MergeTuple8<>(t, t1, t2, t3, t4, t5, t6, t7));
        });
    }

}
