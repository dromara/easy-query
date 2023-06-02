package com.easy.query.api4j.delete.impl;

import com.easy.query.api4j.delete.EntityDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.function.Function;

/**
 * @FileName: EasyEmptyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:22
 * @author xuejiaming
 */
public class EasyEmptyEntityDeletable<T> implements EntityDeletable<T> {
    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if(rows!=expectRows){
            throw new EasyQueryConcurrentException(msg,code);
        }
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public EntityDeletable<T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public EntityDeletable<T> allowDeleteStatement(boolean allow) {
        return this;
    }

    @Override
    public EntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityDeletable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityDeletable<T> noInterceptor() {
        return this;
    }

    @Override
    public EntityDeletable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public EntityDeletable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityDeletable<T> useInterceptor() {
        return this;
    }
}
