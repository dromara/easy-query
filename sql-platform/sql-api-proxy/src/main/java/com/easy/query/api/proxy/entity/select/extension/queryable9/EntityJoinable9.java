package com.easy.query.api.proxy.entity.select.extension.queryable9;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable10;
import com.easy.query.core.common.tuple.MergeTuple10;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression10;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends ClientEntityQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, EntityQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {


    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(Class<T10> joinClass, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        T10Proxy t10Proxy = EntityQueryProxyManager.create(joinClass);
        return leftJoin(t10Proxy,on);
    }
    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(T10Proxy t10Proxy, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(t10Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy.create(t9.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy, entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return leftJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(Class<T10> joinClass, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        T10Proxy t10Proxy = EntityQueryProxyManager.create(joinClass);
        return rightJoin(t10Proxy,on);
    }
    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(T10Proxy t10Proxy, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(t10Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy.create(t9.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy, entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return rightJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(Class<T10> joinClass, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        T10Proxy t10Proxy = EntityQueryProxyManager.create(joinClass);
        return innerJoin(t10Proxy,on);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(T10Proxy t10Proxy, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(t10Proxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy.create(t9.getTable(),get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), t10Proxy, entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy> on) {
        if(EasySQLExpressionUtil.useTableForJoin(joinQueryable.getSQLEntityExpressionBuilder())){
            return innerJoin(joinQueryable.get1Proxy(),on);
        }
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            get1Proxy().getEntitySQLContext()._where(t.getFilter(), () -> {
                on.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable(), get1Proxy().getEntitySQLContext()));
            });
        });
        return new EasyEntityQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);
    }


    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoinMerge(Class<T10> joinClass, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return leftJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoinMerge(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return leftJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoinMerge(Class<T10> joinClass, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return rightJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoinMerge(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return rightJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10 extends ProxyEntityAvailable<T10, T10Proxy>> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoinMerge(Class<T10> joinClass, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return innerJoin(joinClass, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> EntityQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoinMerge(EntityQueryable<T10Proxy, T10> joinQueryable, SQLActionExpression1<MergeTuple10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        return innerJoin(joinQueryable, (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MergeTuple10<>(t, t1, t2, t3, t4, t5, t6, t7, t8, t9));
        });
    }

}
