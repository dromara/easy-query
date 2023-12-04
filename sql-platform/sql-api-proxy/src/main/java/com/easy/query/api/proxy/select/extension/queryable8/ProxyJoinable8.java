package com.easy.query.api.proxy.select.extension.queryable8;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable9;
import com.easy.query.api.proxy.select.extension.queryable8.join.EasyProxyJoinOnQueryable9;
import com.easy.query.api.proxy.select.extension.queryable8.join.ProxyJoinOnQueryable9;
import com.easy.query.api.proxy.select.extension.queryable9.sql.MultiProxyFilter9;
import com.easy.query.api.proxy.select.extension.queryable9.sql.impl.MultiProxyFilter9Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable9;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/16 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> extends ClientProxyQueryable8Available<T1, T2, T3, T4, T5, T6, T7, T8>, ProxyQueryable8Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(T9Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy,t7Proxy,t8Proxy, joinProxy, queryable);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(ProxyQueryable<T9Proxy, T9> joinQueryable) {
        ClientQueryable<T9> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy,t8Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(T9Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy,t7Proxy,t8Proxy, joinProxy, queryable);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(ProxyQueryable<T9Proxy, T9> joinQueryable) {
        ClientQueryable<T9> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy,t8Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(T9Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy,t7Proxy,t8Proxy, joinProxy, queryable);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyJoinOnQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(ProxyQueryable<T9Proxy, T9> joinQueryable) {
        ClientQueryable<T9> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T9> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable8().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable9(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(),t8Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable9<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy,t8Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);
    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> leftJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> rightJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(T9Proxy joinProxy, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinProxy.create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinProxy, entityQueryable9);

    }

    default <T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> ProxyQueryable9<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> innerJoin(ProxyQueryable<T9Proxy, T9> joinQueryable, SQLExpression1<MultiProxyFilter9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>> on) {
        ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9 = getClientQueryable8().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
            on.apply(new MultiProxyFilter9Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), joinQueryable.get1Proxy().create(t8.getTable())));
        });
        return new EasyProxyQueryable9<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(),get8Proxy(), joinQueryable.get1Proxy(), entityQueryable9);

    }
}
