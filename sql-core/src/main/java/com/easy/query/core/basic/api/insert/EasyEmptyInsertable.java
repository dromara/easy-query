package com.easy.query.core.basic.api.insert;

import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.expression.sql.EntityInsertExpression;

import java.util.function.Function;

/**
 * @FileName: EasyEmptyInsertable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:49
 * @author xuejiaming
 */
public class EasyEmptyInsertable<T>implements Insertable<T> {
    private final EntityInsertExpression entityInsertExpression;

    public EasyEmptyInsertable(EntityInsertExpression entityInsertExpression){

        this.entityInsertExpression = entityInsertExpression;
    }
    @Override
    public Insertable<T> insert(T entity) {
        if(entity==null){
            return  this;
        }
        EasySqlApiFactory sqlApiFactory = entityInsertExpression.getRuntimeContext().getSqlApiFactory();
        Insertable<T> insertable = (Insertable<T>) sqlApiFactory.createInsertable((Class<T>) entity.getClass(), entityInsertExpression);
        insertable.insert(entity);
        return insertable;
    }

    @Override
    public Insertable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return 0;
    }

    @Override
    public String toSql() {
        return null;
    }

    @Override
    public Insertable<T> noInterceptor() {
        return this;
    }
    @Override
    public Insertable<T> useInterceptor(String name) {
        return this;
    }
    @Override
    public Insertable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public Insertable<T> useInterceptor() {
        return this;
    }
}
