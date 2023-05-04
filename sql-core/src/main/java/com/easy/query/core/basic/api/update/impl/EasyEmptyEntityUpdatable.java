package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlColumnSelector;

import java.util.function.Function;

/**
 * @FileName: EasyEmptyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 * @author xuejiaming
 */
public class EasyEmptyEntityUpdatable<T> implements EntityUpdatable<T> {
    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public EntityUpdatable<T> setSqlStrategy(boolean condition, SqlExecuteStrategyEnum sqlStrategy) {
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
