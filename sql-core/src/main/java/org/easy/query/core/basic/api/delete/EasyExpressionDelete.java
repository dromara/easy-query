package org.easy.query.core.basic.api.delete;

import org.easy.query.core.basic.api.update.ExpressionUpdate;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: EasyExpressionDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:24
 * @Created by xuejiaming
 */
public interface EasyExpressionDelete<T> extends EasyDelete<T> {
    default EasyExpressionDelete<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
        return where(true,whereExpression);
    }
    EasyExpressionDelete<T> where(boolean condition,SqlExpression<SqlPredicate<T>> whereExpression);
    EasyDelete<T> deleteById(Object id);
}
