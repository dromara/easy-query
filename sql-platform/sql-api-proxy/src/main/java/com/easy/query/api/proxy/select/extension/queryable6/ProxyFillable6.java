package com.easy.query.api.proxy.select.extension.queryable6;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable6;
import com.easy.query.api.proxy.sql.ProxyFill;
import com.easy.query.api.proxy.sql.impl.ProxyFillImpl;
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
public interface ProxyFillable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientProxyQueryable6Available<T1, T2, T3, T4, T5, T6>, ProxyQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {


    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity,TProperty> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> fillMany(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable6().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImpl(fillSelector)).getClientQueryable();
        }, targetProperty.getValue(), selfProperty, produce);
        return getQueryable6();
    }

    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity,TProperty> ProxyQueryable6<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> fillOne(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable6().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImpl(fillSelector)).getClientQueryable();
        }, targetProperty.getValue(), selfProperty, produce);
        return getQueryable6();
    }
}
