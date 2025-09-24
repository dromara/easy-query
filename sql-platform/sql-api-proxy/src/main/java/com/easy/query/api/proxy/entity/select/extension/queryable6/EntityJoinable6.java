package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable7;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression7;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {


    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(Class<T7> joinClass, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        T7Proxy t7Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t7Proxy,on);
    }
    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(T7Proxy t7Proxy, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(t7Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy.create(t6.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy, entityQueryable7);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return leftJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(Class<T7> joinClass, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        T7Proxy t7Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t7Proxy,on);
    }
    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(T7Proxy t7Proxy, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(t7Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy.create(t6.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy, entityQueryable7);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return rightJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(Class<T7> joinClass, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        T7Proxy t7Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t7Proxy,on);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(T7Proxy t7Proxy, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(t7Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy.create(t6.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), t7Proxy, entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return innerJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }


    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoinMerge(Class<T7> joinClass, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoinMerge(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoinMerge(Class<T7> joinClass, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoinMerge(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7 extends ProxyEntityAvailable<T7, T7Proxy>> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoinMerge(Class<T7> joinClass, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> EntityQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoinMerge(EntityQueryable<T7Proxy, T7> joinQueryable, SQLActionExpression1<MergeTuple7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MergeTuple7<>(t, t1, t2, t3, t4, t5, t6));
        });
    }

}
