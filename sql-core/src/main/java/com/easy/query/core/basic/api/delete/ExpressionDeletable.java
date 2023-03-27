package com.easy.query.core.basic.api.delete;

import com.easy.query.core.basic.api.update.ExpressionUpdatable;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @author xuejiaming
 */
public interface ExpressionDeletable<T> extends Deletable<T, ExpressionDeletable<T>> {
    default ExpressionDeletable<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    ExpressionDeletable<T> where(boolean condition, SqlExpression<SqlPredicate<T>> whereExpression);


    default ExpressionDeletable<T> withVersion(Object versionValue){
        return withVersion(true,versionValue);
    }
    ExpressionDeletable<T> withVersion(boolean condition,Object versionValue);

    Deletable<T, ExpressionDeletable<T>> whereById(Object id);
}
