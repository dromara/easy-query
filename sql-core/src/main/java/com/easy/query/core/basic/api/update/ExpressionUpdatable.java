package com.easy.query.core.basic.api.update;

import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

/**
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:21
 * @Created by xuejiaming
 */
public interface ExpressionUpdatable<T> extends Updatable<T> {
   default ExpressionUpdatable<T> set(SqlExpression<SqlColumnSetter<T>> setExpression){
       return set(true,setExpression);
   }
    ExpressionUpdatable<T> set(boolean condition,SqlExpression<SqlColumnSetter<T>> setExpression);
   default ExpressionUpdatable<T> where(SqlExpression<SqlPredicate<T>> whereExpression){
       return where(true,whereExpression);
   }
    ExpressionUpdatable<T> where(boolean condition,SqlExpression<SqlPredicate<T>> whereExpression);
}

