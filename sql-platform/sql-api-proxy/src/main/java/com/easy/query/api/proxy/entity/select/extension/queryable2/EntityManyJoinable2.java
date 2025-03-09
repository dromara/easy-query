package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ManyPropColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2025/3/7 21:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityManyJoinable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {
    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> manyJoin(SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<T3Proxy, T3>> manyPropColumnExpression) {
        return manyJoin(true, manyPropColumnExpression, null);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> manyJoin(SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<T3Proxy, T3>> manyPropColumnExpression,
                                                                                                                                                         SQLFuncExpression1<EntityQueryable<T3Proxy, T3>, EntityQueryable<T3Proxy, T3>> adapterExpression) {
        return manyJoin(true, manyPropColumnExpression, adapterExpression);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> manyJoin(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<T3Proxy, T3>> manyPropColumnExpression) {
        return manyJoin(condition, manyPropColumnExpression, null);
    }

    default <T3Proxy extends ProxyEntity<T3Proxy, T3>, T3 extends ProxyEntityAvailable<T3, T3Proxy>> EntityQueryable2<T1Proxy, T1, T2Proxy, T2> manyJoin(boolean condition, SQLFuncExpression2<T1Proxy, T2Proxy, ManyPropColumn<T3Proxy, T3>> manyPropColumnExpression,
                                                                                                                                                         SQLFuncExpression1<EntityQueryable<T3Proxy, T3>, EntityQueryable<T3Proxy, T3>> adapterExpression) {
        if(condition){

            ValueHolder<ManyPropColumn<T3Proxy, T3>> valueHolder = new ValueHolder<>();
            get1Proxy().getEntitySQLContext()._include(() -> {
                ManyPropColumn<T3Proxy, T3> value = manyPropColumnExpression.apply(get1Proxy(),get2Proxy());
                valueHolder.setValue(value);
            });
            T3Proxy proxy = valueHolder.getValue().getProxy();
            String value = valueHolder.getValue().getValue();
            if (adapterExpression == null) {

                getClientQueryable2().manyJoin((mjs1,mjs2) -> mjs1.manyColumn(proxy.getTable(),value), null);
            } else {
                getClientQueryable2().manyJoin((mjs1,mjs2) -> mjs1.manyColumn(proxy.getTable(),value), cq -> {
                    ClientQueryable<T3> innerClientQueryable = EasyObjectUtil.typeCastNullable(cq);
                    T3Proxy tPropertyProxy = EntityQueryProxyManager.create(innerClientQueryable.queryClass());
                    EasyEntityQueryable<T3Proxy, T3> entityQueryable = new EasyEntityQueryable<>(tPropertyProxy, innerClientQueryable);
                    adapterExpression.apply(entityQueryable);
                    return entityQueryable.getClientQueryable();
                });
            }
        }
        return getQueryable2();
    }

}
