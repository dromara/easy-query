package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.EntityObjectDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.function.Function;

/**
 * create time 2023/6/2 13:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyEntityObjectDeletable<T> implements EntityObjectDeletable<T> {
    @Override
    public ExpressionContext getExpressionContext() {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public EntityObjectDeletable<T> allowDeleteStatement(boolean allow) {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> noInterceptor() {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> useInterceptor() {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            throw new EasyQueryConcurrentException(msg, code);
        }
    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public EntityObjectDeletable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityObjectDeletable<T> asAlias(String alias) {
        return this;
    }
}
