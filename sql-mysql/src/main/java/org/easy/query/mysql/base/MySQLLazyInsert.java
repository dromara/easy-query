package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.insert.Insertable;
import org.easy.query.core.query.SqlEntityInsertExpression;

/**
 * @FileName: MySQLazyInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/23 22:49
 * @Created by xuejiaming
 */
public class MySQLLazyInsert<T> implements Insertable<T> {
    private final SqlEntityInsertExpression sqlEntityInsertExpression;

    public MySQLLazyInsert(SqlEntityInsertExpression sqlEntityInsertExpression){

        this.sqlEntityInsertExpression = sqlEntityInsertExpression;
    }
    @Override
    public Insertable<T> insert(T entity) {
    if(entity==null){
        return  this;
    }
    MySQLInsert<T> tMySQLInsert = new MySQLInsert<>((Class<T>) entity.getClass(), sqlEntityInsertExpression);
    tMySQLInsert.insert(entity);
    return tMySQLInsert;
}

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public String toSql() {
        return null;
    }
}
