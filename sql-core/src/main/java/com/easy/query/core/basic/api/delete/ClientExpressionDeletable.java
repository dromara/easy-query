package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 */
public interface ClientExpressionDeletable<T> extends Deletable<T, ClientExpressionDeletable<T>>, Versionable<ClientExpressionDeletable<T>> {
    default ClientExpressionDeletable<T> where(SQLExpression1<WherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    ClientExpressionDeletable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression);


    default ClientExpressionDeletable<T> whereById(Object id) {
        return whereById(true, id);
    }

    ClientExpressionDeletable<T> whereById(boolean condition, Object id);


    default <TProperty> ClientExpressionDeletable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ClientExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids);
}
