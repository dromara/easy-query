package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable7;
import com.easy.query.api.proxy.select.extension.queryable6.join.EasyProxyJoinOnQueryable7;
import com.easy.query.api.proxy.select.extension.queryable6.join.ProxyJoinOnQueryable7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.MultiProxyFilter7;
import com.easy.query.api.proxy.select.extension.queryable7.sql.impl.MultiProxyFilter7Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable7;
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
public interface ProxyJoinable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4,T5,T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(T7Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinProxy, queryable);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(ProxyQueryable<T7Proxy, T7> joinQueryable) {

        ClientQueryable<T7> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T7> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable7<T1, T2, T3, T4, T5, T6,T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.LEFT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(T7Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinProxy, queryable);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(ProxyQueryable<T7Proxy, T7> joinQueryable) {

        ClientQueryable<T7> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T7> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable7<T1, T2, T3, T4, T5, T6,T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.INNER_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinQueryable.get1Proxy(), queryable);
    }
    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(T7Proxy joinProxy) {
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), joinProxy.getEntityClass(), MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinProxy, queryable);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyJoinOnQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(ProxyQueryable<T7Proxy, T7> joinQueryable) {

        ClientQueryable<T7> joinClientQueryable = joinQueryable.getClientQueryable();
        ClientQueryable<T7> selectAllTQueryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinClientQueryable);
        T1Proxy t1Proxy = get1Proxy();
        T2Proxy t2Proxy = get2Proxy();
        T3Proxy t3Proxy = get3Proxy();
        T4Proxy t4Proxy = get4Proxy();
        T5Proxy t5Proxy = get5Proxy();
        T6Proxy t6Proxy = get6Proxy();
        EntityQueryExpressionBuilder sqlEntityExpressionBuilder = getClientQueryable6().getSQLEntityExpressionBuilder();
        sqlEntityExpressionBuilder.getExpressionContext().extract(selectAllTQueryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable7<T1, T2, T3, T4, T5, T6,T7> queryable = sqlEntityExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable7(t1Proxy.getEntityClass(), t2Proxy.getEntityClass(), t3Proxy.getEntityClass(), t4Proxy.getEntityClass(), t5Proxy.getEntityClass(),t6Proxy.getEntityClass(), selectAllTQueryable, MultiTableTypeEnum.RIGHT_JOIN, sqlEntityExpressionBuilder);
        return new EasyProxyJoinOnQueryable7<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy,t6Proxy, joinQueryable.get1Proxy(), queryable);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(T7Proxy joinProxy, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);
    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> leftJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().leftJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(T7Proxy joinProxy, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> rightJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().rightJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(T7Proxy joinProxy, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinProxy.getEntityClass(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy.create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinProxy, entityQueryable7);

    }

    default <T7Proxy extends ProxyEntity<T7Proxy, T7>, T7> ProxyQueryable7<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7> innerJoin(ProxyQueryable<T7Proxy, T7> joinQueryable, SQLExpression1<MultiProxyFilter7<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy>> on) {
        ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7 = getClientQueryable6().innerJoin(joinQueryable.getClientQueryable(), (t, t1, t2, t3, t4, t5, t6) -> {
            on.apply(new MultiProxyFilter7Impl<>(t.getFilter(), get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(), get6Proxy(), joinQueryable.get1Proxy().create(t6.getTable())));
        });
        return new EasyProxyQueryable7<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(), joinQueryable.get1Proxy(), entityQueryable7);

    }
}
