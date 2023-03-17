package com.easy.query.core.basic.api.update;

import com.easy.query.core.basic.api.abstraction.SqlExecuteRows;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @Created by xuejiaming
 */
public interface Updatable<T> extends SqlExecuteRows {
//    Insert<T> insertColumns(SqlExpression<SqlPredicate<T>> columnExpression);
//    Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression);

    String toSql();
}
