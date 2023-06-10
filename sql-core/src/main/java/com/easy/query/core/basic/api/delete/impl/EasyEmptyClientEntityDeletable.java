package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
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
public class EasyEmptyClientEntityDeletable<T> implements ClientEntityDeletable<T> {
    @Override
    public ExpressionContext getExpressionContext() {
        return null;
    }

    @Override
    public String toSQL()  {
        return null;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public ClientEntityDeletable<T> allowDeleteStatement(boolean allow) {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> noInterceptor() {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> useInterceptor() {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> useLogicDelete(boolean enable) {
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
    public ClientEntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public ClientEntityDeletable<T> asAlias(String alias) {
        return this;
    }
}
