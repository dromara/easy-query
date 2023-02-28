package org.easy.query.core.basic.api.update;

/**
 * @FileName: Update.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:04
 * @Created by xuejiaming
 */
public interface Update<T> {
//    Insert<T> insertColumns(SqlExpression<SqlPredicate<T>> columnExpression);
//    Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression);

    /**
     * 返回受影响行数
     * @return
     */
    long executeRows();
    void executeRows(Long expectRow,String error);
    String toSql();
}
