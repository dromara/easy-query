package org.easy.query.core.basic.api;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;

/**
 * @FileName: EntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 23:22
 * @Created by xuejiaming
 */
public interface EntityUpdate<T> extends Update<T> {
    default EntityUpdate<T> setColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
        return setColumns(true,columnSelectorExpression);
    }
    EntityUpdate<T> setColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
    default EntityUpdate<T> setIgnoreColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
        return setIgnoreColumns(true,columnSelectorExpression);
    }
    EntityUpdate<T> setIgnoreColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
   default EntityUpdate<T> whereColumns(SqlExpression<SqlColumnSelector<T>> columnSelectorExpression){
       return whereColumns(true,columnSelectorExpression);
   }
    EntityUpdate<T> whereColumns(boolean condition,SqlExpression<SqlColumnSelector<T>> columnSelectorExpression);
}
