package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.sql.ProxyEntityFill;
import com.easy.query.api.proxy.sql.impl.ProxyEntityFillImpl;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * create time 2023/8/17 13:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFillable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {


    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>, TProperty> EntityQueryable<T1Proxy, T1> fillMany(Class<TREntity> subEntityClass, SQLFuncExpression1<ProxyEntityFill<TRProxyEntity, TREntity>, EntityQueryable<TRProxyEntity, TREntity>> fillSetterExpression, Function<TRProxyEntity, SQLColumn<TRProxyEntity, TProperty>> targetPropertyFunction, Property<T1, TProperty> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        TRProxyEntity trProxyEntity = EntityQueryProxyManager.create(subEntityClass);
        SQLColumn<TRProxyEntity, TProperty> targetProperty = targetPropertyFunction.apply(trProxyEntity);
        getClientQueryable().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new ProxyEntityFillImpl<>(fillSelector)).getClientQueryable();
        }, targetProperty.getValue(), selfProperty, produce);
        return getQueryable();
    }

    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity extends ProxyEntityAvailable<TREntity, TRProxyEntity>, TProperty> EntityQueryable<T1Proxy, T1> fillOne(Class<TREntity> subEntityClass,SQLFuncExpression1<ProxyEntityFill<TRProxyEntity, TREntity>, EntityQueryable<TRProxyEntity, TREntity>> fillSetterExpression, Function<TRProxyEntity, SQLColumn<TRProxyEntity, TProperty>> targetPropertyFunction, Property<T1, TProperty> selfProperty, BiConsumer<T1, TREntity> produce) {
        TRProxyEntity trProxyEntity = EntityQueryProxyManager.create(subEntityClass);
        SQLColumn<TRProxyEntity, TProperty> targetProperty = targetPropertyFunction.apply(trProxyEntity);
        getClientQueryable().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new ProxyEntityFillImpl<>(fillSelector)).getClientQueryable();
        }, targetProperty.getValue(), selfProperty, produce);
        return getQueryable();
    }
}
