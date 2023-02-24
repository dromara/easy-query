package org.easy.query.core.basic.api;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.base.SqlColumnSetter;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;

/**
 * @FileName: ExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 * @Created by xuejiaming
 */
public interface ExpressionUpdate<T> extends Update<T> {
    Update<T> set(SqlExpression<SqlColumnSetter<T>> setExpression);
    Update<T> where(SqlExpression<SqlPredicate<T>> whereExpression);
}
