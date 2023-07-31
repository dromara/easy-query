package com.easy.query.api4j.delete;

import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.core.basic.api.delete.Deletable;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.Collection;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>>, WithVersionable<ExpressionDeletable<T>>, ConfigureVersionable<ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SQLExpression1<SQLWherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    ExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression);


    default ExpressionDeletable<T> whereById(Object id) {
        return whereById(true, id);
    }

    ExpressionDeletable<T> whereById(boolean condition, Object id);

    default <TProperty> ExpressionDeletable<T> whereByIds(Collection<TProperty> ids) {
        return whereByIds(true, ids);
    }

    <TProperty> ExpressionDeletable<T> whereByIds(boolean condition, Collection<TProperty> ids);
}
