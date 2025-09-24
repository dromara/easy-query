package com.easy.query.api.proxy.entity.select.extension.queryable4;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;
import com.easy.query.core.common.tuple.MergeTuple5;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression5;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4> extends ClientEntityQueryable4Available<T1, T2, T3, T4>, EntityQueryable4Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass
     * @param on
     * @param <T5Proxy>
     * @param <T5>
     * @return
     */

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoin(Class<T5> joinClass, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        T5Proxy t5Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t5Proxy,on);
    }
    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoin(T5Proxy t5Proxy, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(t5Proxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy.create(t4.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy, entityQueryable5);
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoin(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return leftJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoin(Class<T5> joinClass, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        T5Proxy t5Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t5Proxy,on);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoin(T5Proxy t5Proxy, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(t5Proxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy.create(t4.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy, entityQueryable5);

    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoin(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return rightJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoin(Class<T5> joinClass, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        T5Proxy t5Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t5Proxy,on);
    }
    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoin(T5Proxy t5Proxy, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(t5Proxy.getEntityClass(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy.create(t4.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), t5Proxy, entityQueryable5);
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoin(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return innerJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable5<T1, T2, T3, T4, T5> entityQueryable5 = getClientQueryable4().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(),()->{
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy().create(t4.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable5<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), joinQueryable.get1Proxy(), entityQueryable5);
    }


    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoinMerge(Class<T5> joinClass, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> leftJoinMerge(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoinMerge(Class<T5> joinClass, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return rightJoin(joinClass, ( t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> rightJoinMerge(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5 extends ProxyEntityAvailable<T5,T5Proxy>> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoinMerge(Class<T5> joinClass, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

    default <T5Proxy extends ProxyEntity<T5Proxy, T5>, T5> EntityQueryable5<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5> innerJoinMerge(EntityQueryable<T5Proxy, T5> joinQueryable, SQLActionExpression1<MergeTuple5<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4) -> {
            on.apply(new MergeTuple5<>(t, t1, t2, t3, t4));
        });
    }

}
