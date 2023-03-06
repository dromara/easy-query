package org.easy.query.core.basic.api.update;

import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;

/**
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 * @Created by xuejiaming
 */
public interface EntityUpdatable<T> extends Updatable<T> {
    default EntityUpdatable<T> setColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
        return setColumns(true,columnSelectorExpression);
    }
    EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
    default EntityUpdatable<T> setIgnoreColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
        return setIgnoreColumns(true,columnSelectorExpression);
    }
    EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
   default EntityUpdatable<T> whereColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
       return whereColumns(true,columnSelectorExpression);
   }
    EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
}
