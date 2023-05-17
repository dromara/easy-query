package com.easy.query.core.basic.api.insert;

import com.easy.query.core.abstraction.EasySQLApiFactory;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.function.Function;

/**
 * @FileName: EasyEmptyInsertable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:49
 * @author xuejiaming
 */
public class EasyEmptyInsertable<T>implements Insertable<T> {
    private final EntityInsertExpressionBuilder entityInsertExpression;

    public EasyEmptyInsertable(EntityInsertExpressionBuilder entityInsertExpression){

        this.entityInsertExpression = entityInsertExpression;
    }
    @Override
    public Insertable<T> insert(T entity) {
        if(entity==null){
            return  this;
        }
        EasySQLApiFactory sqlApiFactory = entityInsertExpression.getRuntimeContext().getSQLApiFactory();
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

    @Override
    public Insertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }
}
