package com.easy.query.api.proxy.delete;

import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ProxyExpressionDeletable<TProxy extends ProxyEntity<TProxy,T>,T> extends ProxyDeletable<TProxy,T, ProxyExpressionDeletable<TProxy,T>>, WithVersionable<ProxyExpressionDeletable<TProxy,T>> , ConfigureVersionable<ProxyExpressionDeletable<TProxy,T>> {
    default ProxyExpressionDeletable<TProxy,T> where(SQLExpression2<ProxyFilter,TProxy> whereExpression) {
        return where(true, whereExpression);
    }

    ProxyExpressionDeletable<TProxy,T> where(boolean condition, SQLExpression2<ProxyFilter,TProxy> whereExpression);
 default ProxyExpressionDeletable<TProxy,T> where(SQLExpression1<ProxyFilter> whereExpression) {
        return where(true, whereExpression);
    }

    ProxyExpressionDeletable<TProxy,T> where(boolean condition, SQLExpression1<ProxyFilter> whereExpression);


    default ProxyExpressionDeletable<TProxy,T> whereById(Object id) {
        return whereById(true, id);
    }

    ProxyExpressionDeletable<TProxy,T> whereById(boolean condition, Object id);

    default <TProperty> ProxyExpressionDeletable<TProxy,T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ProxyExpressionDeletable<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids);
}
