package com.easy.query.api.proxy.entity.delete;

import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * create time 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends Deletable<T, ExpressionDeletable<TProxy,T>>, WithVersionable<ExpressionDeletable<TProxy,T>>, ConfigureVersionable<ExpressionDeletable<TProxy,T>> {
    default ExpressionDeletable<TProxy,T> where(SQLActionExpression1<TProxy> whereExpression) {
        return where(true, whereExpression);
    }

    ExpressionDeletable<TProxy,T> where(boolean condition, SQLActionExpression1<TProxy> whereExpression);


    default ExpressionDeletable<TProxy,T> whereById(Object id) {
        return whereById(true, id);
    }

    ExpressionDeletable<TProxy,T> whereById(boolean condition, Object id);

    default <TProperty> ExpressionDeletable<TProxy,T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ExpressionDeletable<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids);
}
