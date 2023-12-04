package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.common.tuple.Tuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLPredicate;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityJoinable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {

    /**
     * <pre>{@code
     * leftJoin(Entity2.class, (t1, t2,t3) -> t.eq(t2, Entity::getId, Entity2::getId))
     * }</pre>
     * t代表from的表,t1代表第一次join的表,t2代表第二次join的表
     *
     * @param joinClass 和哪张表进行join
     * @param on        条件
     * @param <T3>
     * @return 返回可查询的表达式支持3表参数
     */

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> leftJoin(Class<T3> joinClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinClass, (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable()));
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  leftJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy());
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  rightJoin(Class<T3> joinClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinClass, (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable()));
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  rightJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy());
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  innerJoin(Class<T3> joinClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        T3Proxy t3Proxy = EntityQueryProxyManager.create(joinClass);
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinClass, (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), t3Proxy.create(t2.getTable()));
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), t3Proxy, entityQueryable3);

    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  innerJoin(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLPredicate> on) {
        ClientQueryable3<T1, T2, T3> entityQueryable3 = getClientQueryable2().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2) -> {
            SQLPredicate sqlPredicate = on.apply(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy());
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyEntityQueryable3<>(get1Proxy(), get2Proxy(), joinQueryable.get1Proxy(), entityQueryable3);

    }


    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  leftJoinMerge(Class<T3> joinClass, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return leftJoin(joinClass, (t1, t2, t3) -> {
            return on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  leftJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return leftJoin(joinQueryable, (t1, t2, t3) -> {
            return on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  rightJoinMerge(Class<T3> joinClass, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return rightJoin(joinClass, (t1, t2, t3) -> {
            return on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  rightJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return rightJoin(joinQueryable, (t1, t2, t3) -> {
            return on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  innerJoinMerge(Class<T3> joinClass, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return innerJoin(joinClass, (t1, t2, t3) -> {
            return on.apply( new Tuple3<>(t1, t2, t3));
        });
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3,T3Proxy>> EntityQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3>  innerJoinMerge(EntityQueryable<T3Proxy, T3> joinQueryable, SQLFuncExpression1<Tuple3<T1Proxy, T2Proxy, T3Proxy>,SQLPredicate> on) {
        return innerJoin(joinQueryable, (t1, t2, t3) -> {
            return on.apply(new Tuple3<>(t1, t2, t3));
        });
    }

}
