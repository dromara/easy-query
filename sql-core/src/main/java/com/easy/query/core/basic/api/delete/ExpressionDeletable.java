package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;

import java.util.Collection;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>>, Versionable<ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SQLExpression1<SQLWherePredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    ExpressionDeletable<T> where(boolean condition, SQLExpression1<SQLWherePredicate<T>> whereExpression);


    Deletable<T, ExpressionDeletable<T>> whereById(Object id);
    Deletable<T, ExpressionDeletable<T>> whereByIds(Object ...ids);
    Deletable<T, ExpressionDeletable<T>> whereByIds(Collection<?> ids);
}
