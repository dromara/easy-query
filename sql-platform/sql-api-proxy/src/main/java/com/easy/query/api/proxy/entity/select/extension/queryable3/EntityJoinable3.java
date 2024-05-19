package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T4>
     * @return 返回可查询的表达式支持3表参数
     */

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(Class<T4> joinClass, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        T4Proxy t4Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t4Proxy,on);
    }
    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(T4Proxy t4Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(t4Proxy.getEntityClass(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), t4Proxy.create(t3.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), t4Proxy, entityQueryable4);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoin(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy().create(t3.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(Class<T4> joinClass, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        T4Proxy t4Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t4Proxy,on);

    }
    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(T4Proxy t4Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(t4Proxy.getEntityClass(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), t4Proxy.create(t3.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), t4Proxy, entityQueryable4);

    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoin(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy().create(t3.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(Class<T4> joinClass, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        T4Proxy t4Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t4Proxy,on);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(T4Proxy t4Proxy, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(t4Proxy.getEntityClass(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), t4Proxy.create(t3.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), t4Proxy, entityQueryable4);
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoin(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression4<T1Proxy, T2Proxy, T3Proxy, T4Proxy> on) {
        ClientQueryable4<T1, T2, T3, T4> entityQueryable4 = getClientQueryable3().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), joinQueryable.get1Proxy().create(t3.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable4<>(get1Proxy(), get2Proxy(),get3Proxy(), joinQueryable.get1Proxy(), entityQueryable4);
    }


    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoinMerge(Class<T4> joinClass, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return leftJoin(joinClass, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> leftJoinMerge(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return leftJoin(joinQueryable, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoinMerge(Class<T4> joinClass, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return rightJoin(joinClass, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> rightJoinMerge(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return rightJoin(joinQueryable, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4 extends ProxyEntityAvailable<T4,T4Proxy>> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoinMerge(Class<T4> joinClass, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return innerJoin(joinClass, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

    default <T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> EntityQueryable4<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> innerJoinMerge(EntityQueryable<T4Proxy, T4> joinQueryable, SQLExpression1<MergeTuple4<T1Proxy, T2Proxy, T3Proxy,T4Proxy>> on) {
        return innerJoin(joinQueryable, (t1, t2, t3,t4) -> {
            on.apply(new MergeTuple4<>(t1, t2, t3,t4));
        });
    }

}
