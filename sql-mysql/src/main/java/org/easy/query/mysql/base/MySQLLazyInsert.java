package org.easy.query.mysql.base;

import org.easy.query.core.basic.api.Insert;
import org.easy.query.core.impl.InsertContext;

/**
 * @FileName: MySQLazyInsert.java
 * @Description: 文件说明
 * @Date: 2023/2/23 22:49
 * @Created by xuejiaming
 */
public class MySQLLazyInsert<T> implements Insert<T> {
    private final InsertContext insertContext;

    public MySQLLazyInsert(InsertContext insertContext){

        this.insertContext = insertContext;
    }
    @Override
    public Insert<T> insert(T entity) {
    if(entity==null){
        return  this;
    }
    MySQLInsert<T> tMySQLInsert = new MySQLInsert<>((Class<T>) entity.getClass(), insertContext);
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
