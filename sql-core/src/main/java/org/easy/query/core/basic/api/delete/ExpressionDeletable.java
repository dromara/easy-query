package org.easy.query.core.basic.api.delete;

import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @Created by xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    ExpressionDeletable<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression);
    Deletable<T, ExpressionDeletable<T>> deleteById(Object id);
}
