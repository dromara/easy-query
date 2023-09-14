package com.easy.query.api.proxy.select.extension.queryable3;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable3;
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
public interface ProxyFillable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientProxyQueryable3Available<T1, T2, T3>, ProxyQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {


    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity,TProperty> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> fillMany(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable3().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable3();
    }

    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity,TProperty> ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> fillOne(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<TRProxyEntity,TProperty> targetProperty, Property<T1, TProperty> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable3().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable3();
    }
}
