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
   default ExpressionUpdate<T> set(SqlExpression<SqlColumnSetter<T>> setExpression){
       return set(true,setExpression);
   }
    ExpressionUpdate<T> set(boolean condition,SqlExpression<SqlColumnSetter<T>> setExpression);
   default ExpressionUpdate<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
       return where(true,whereExpression);
   }
    ExpressionUpdate<T> where(boolean condition,SqlExpression<SqlPredicate<T>> whereExpression);
}
