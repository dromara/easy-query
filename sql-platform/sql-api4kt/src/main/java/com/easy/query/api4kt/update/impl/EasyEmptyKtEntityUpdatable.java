package com.easy.query.api4kt.update.impl;

import com.easy.query.api4kt.sql.SQLKtColumnSelector;
import com.easy.query.api4kt.update.KtEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEmptyEntityUpdate.java
 * @Description: 文件说明
 * @Date: 2023/3/6 12:21
 */
public class EasyEmptyKtEntityUpdatable<T> implements KtEntityUpdatable<T> {
    @Override
    public KtEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLKtColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return null;
    }

    @Override
    public KtEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            throw new EasyQueryConcurrentException(msg, code);
        }
    }

    @Override
    public KtEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor() {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor() {
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useLogicDelete(boolean enable) {
        return this;
    }
}
