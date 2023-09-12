package com.easy.query.api.proxy.select.extension.queryable2;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
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
public interface ProxyFillable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientProxyQueryable2Available<T1, T2>, ProxyQueryable2Available<T1Proxy, T1, T2Proxy, T2> {


    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> fillMany(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<?,?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, Collection<TREntity>> produce) {
        getClientQueryable2().fillMany(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable2();
    }

    default <TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> fillOne(SQLFuncExpression1<ProxyFill, ProxyQueryable<TRProxyEntity, TREntity>> fillSetterExpression, SQLColumn<?,?> targetProperty, Property<T1, ?> selfProperty, BiConsumer<T1, TREntity> produce) {
        getClientQueryable2().fillOne(fillSelector -> {
            return fillSetterExpression.apply(new ProxyFillImp(fillSelector)).getClientQueryable();
        }, targetProperty.value(), selfProperty, produce);
        return getQueryable2();
    }
}
