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
public interface ExpressionObjectDeletable<T> extends Deletable<T, ExpressionObjectDeletable<T>>, Versionable<ExpressionObjectDeletable<T>> {
    default ExpressionObjectDeletable<T> where(SQLExpression1<WherePredicate<T>> whereExpression) {
        return where(true, whereExpression);
    }

    ExpressionObjectDeletable<T> where(boolean condition, SQLExpression1<WherePredicate<T>> whereExpression);


    default Deletable<T, ExpressionObjectDeletable<T>> whereById(Object id) {
        return whereById(true, id);
    }

    Deletable<T, ExpressionObjectDeletable<T>> whereById(boolean condition, Object id);

    default Deletable<T, ExpressionObjectDeletable<T>> whereByIds(Object... ids) {
        return whereByIds(true, ids);
    }

    Deletable<T, ExpressionObjectDeletable<T>> whereByIds(boolean condition, Object... ids);

    default <TProperty> Deletable<T, ExpressionObjectDeletable<T>> whereByIdCollection(Collection<TProperty> ids) {
        return whereByIdCollection(true, ids);
    }

    <TProperty> Deletable<T, ExpressionObjectDeletable<T>> whereByIdCollection(boolean condition, Collection<TProperty> ids);
}
