package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.internal.Versionable;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>>, Versionable<ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SqlExpression<SqlWherePredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    ExpressionDeletable<T> where(boolean condition, SqlExpression<SqlWherePredicate<T>> whereExpression);


    Deletable<T, ExpressionDeletable<T>> whereById(Object id);
}
