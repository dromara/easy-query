package org.easy.query.core.basic.api.insert;

import java.util.Collection;

/**
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 * @Created by xuejiaming
 */
public interface Insertable<T> {
    Insertable<T> insert(T entity);
   default Insertable<T> insert(Collection<T> entities){
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
    long executeRows();
    String toSql();
}
