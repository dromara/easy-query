package com.easy.query.core.basic.api.update;

import com.easy.query.core.exception.EasyQueryConcurrentException;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @Created by xuejiaming
 */
public interface Updatable<T> {
//    Insert<T> insertColumns(SqlExpression<SqlPredicate<T>> columnExpression);
//    Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression);

    /**
     * 返回受影响行数
     *
     * @return
     */
    long executeRows();

    /**
     * 当执行受影响行数不符合 expectRows 将会抛出 {@link EasyQueryConcurrentException}
     * @param expectRows
     * @param msg
     */
    default void executeRows(long expectRows, String msg) {
        executeRows(expectRows, msg, null);
    }

    /**
     * 异常 EasyQueryConcurrentException
     * @param expectRows
     * @param msg
     * @param code
     */
    void executeRows(long expectRows, String msg, String code);

    String toSql();
}
