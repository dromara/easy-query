package com.easy.query.api4kt.delete;

import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 */
public interface KtExpressionDeletable<T> extends Deletable<T, KtExpressionDeletable<T>>, WithVersionable<KtExpressionDeletable<T>>, ConfigureVersionable<KtExpressionDeletable<T>> {
    default KtExpressionDeletable<T> where(SQLExpression1<SQLKtWherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    KtExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLKtWherePredicate<T>> whereExpression);


    default KtExpressionDeletable<T> whereById(Object id) {
        return whereById(true, id);
    }

    KtExpressionDeletable<T> whereById(boolean condition, Object id);

    default <TProperty> KtExpressionDeletable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> KtExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids);
}
