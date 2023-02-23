package org.easy.query.core.basic.api;

import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.abstraction.sql.base.SqlColumnSelector;

import java.util.Collection;

/**
 * @FileName: Insert.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 * @Created by xuejiaming
 */
public interface Insert<T> {
    Insert<T> insert(T entity);
   default Insert<T> insert(Collection<T> entities){
       for (T entity : entities) {
           insert(entity);
       }
       return this;
   }
//    Insert<T> insertColumns(SqlExpression<SqlPredicate<T>> columnExpression);
//    Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression);

    /**
     * 返回受影响行数
     * @return
     */
    long execute();
    long executeLastInsertId();
    String toSql();
}
