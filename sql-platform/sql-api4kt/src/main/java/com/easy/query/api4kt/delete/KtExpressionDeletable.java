package com.easy.query.api4kt.delete;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 */
public interface KtExpressionDeletable<T> extends Deletable<T, KtExpressionDeletable<T>>, Versionable<KtExpressionDeletable<T>> {
    default KtExpressionDeletable<T> where(SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    KtExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression);


    default Deletable<T, KtExpressionDeletable<T>> whereById(Object id) {
        return whereById(true, id);
    }

    Deletable<T, KtExpressionDeletable<T>> whereById(boolean condition, Object id);

    default Deletable<T, KtExpressionDeletable<T>> whereByIds(Object... ids) {
        return whereByIds(true, ids);
    }

    Deletable<T, KtExpressionDeletable<T>> whereByIds(boolean condition, Object... ids);

    default <TProperty> Deletable<T, KtExpressionDeletable<T>> whereByIdCollection(Collection<TProperty> ids) {
        return whereByIdCollection(true, ids);
    }

    <TProperty> Deletable<T, KtExpressionDeletable<T>> whereByIdCollection(boolean condition, Collection<TProperty> ids);
}
