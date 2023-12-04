package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.sql.ProxyFill;
import com.easy.query.api.proxy.sql.impl.ProxyFillImp;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * create time 2023/8/17 13:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityFillable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy,T1> {



    default <TRProxyEntity extends ProxyEntity<TRProxyEntity,TREntity>,TREntity,TProperty> EntityQueryable<T1Proxy,T1> fillMany(SQLFuncExpression1<ProxyFill, EntityQueryable<TRProxyEntity,TREntity>> fillSetterExpression,SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable();
    }
    default <TRProxyEntity extends ProxyEntity<TRProxyEntity,TREntity>,TREntity,TProperty> EntityQueryable<T1Proxy,T1> fillOne(SQLFuncExpression1<ProxyFill, EntityQueryable<TRProxyEntity,TREntity>> fillSetterExpression, SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable();
    }
}
