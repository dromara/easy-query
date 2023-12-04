package com.easy.query.api.proxy.select.extension.queryable9;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable10;
import com.easy.query.api.proxy.select.extension.queryable10.sql.MultiProxyFilter10;
import com.easy.query.api.proxy.select.extension.queryable10.sql.impl.MultiProxyFilter10Impl;
import com.easy.query.api.proxy.select.extension.queryable9.join.EasyProxyJoinOnQueryable10;
import com.easy.query.api.proxy.select.extension.queryable9.join.ProxyJoinOnQueryable10;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable10;
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
public interface ProxyJoinable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends ClientProxyQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, ProxyQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {
    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(T10Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinProxy, queryable);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(ProxyQueryable<T10Proxy, T10> joinQueryable) {
        ClientQueryable<T10> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T10> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(T10Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinProxy, queryable);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(ProxyQueryable<T10Proxy, T10> joinQueryable) {
        ClientQueryable<T10> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T10> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinQueryable.get1Proxy(), queryable);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(T10Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinProxy, queryable);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyJoinOnQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(ProxyQueryable<T10Proxy, T10> joinQueryable) {
        ClientQueryable<T10> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T10> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        T7Proxy t7Proxy = get7Proxy();
        T8Proxy t8Proxy = get8Proxy();
        T9Proxy t9Proxy = get9Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable9().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable10(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(), t6Proxy.getEntityClass(), t7Proxy.getEntityClass(), t8Proxy.getEntityClass(), t9Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable10<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy, t8Proxy, t9Proxy, joinQueryable.get1Proxy(), queryable);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(T10Proxy joinProxy, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy.create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);
    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> leftJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(T10Proxy joinProxy, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy.create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> rightJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(T10Proxy joinProxy, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy.create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinProxy, entityQueryable10);

    }

    default <T10Proxy extends ProxyEntity<T10Proxy, T10>, T10> ProxyQueryable10<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9, T10Proxy, T10> innerJoin(ProxyQueryable<T10Proxy, T10> joinQueryable, SQLExpression1<MultiProxyFilter10<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, T10Proxy>> on) {
        ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10 = getClientQueryable9().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            on.apply(new MultiProxyFilter10Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy().create(t9.getTable())));
        });
        return new EasyProxyQueryable10<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy(), joinQueryable.get1Proxy(), entityQueryable10);

    }

}
