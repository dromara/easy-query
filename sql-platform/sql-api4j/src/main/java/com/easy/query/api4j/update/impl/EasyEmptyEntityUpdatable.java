package com.easy.query.api4j.update.impl;

import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.function.Function;

/**
 * @FileName: EasyEmptyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @author xuejiaming
 */
public class EasyEmptyEntityUpdatable<T> implements EntityUpdatable<T> {
    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return null;
    }

    @Override
    public EntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg,String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }

    @Override
    public EntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityUpdatable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor() {
        return this;
    }
    @Override
    public EntityUpdatable<T> useInterceptor(String name) {
        return this;
    }
    @Override
    public EntityUpdatable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor() {
        return this;
    }

    @Override
    public EntityUpdatable<T> useLogicDelete(boolean enable) {
        return this;
    }
}
