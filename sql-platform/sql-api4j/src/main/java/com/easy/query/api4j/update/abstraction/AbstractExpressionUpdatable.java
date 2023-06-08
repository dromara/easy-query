package com.easy.query.api4j.update.abstraction;

import com.easy.query.api4j.update.ExpressionUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractExpressionUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/25 08:24
 */
public abstract class AbstractExpressionUpdatable<T> implements ExpressionUpdatable<T> {
    protected final ClientExpressionUpdatable<T> expressionObjectUpdatable;

    public AbstractExpressionUpdatable(ClientExpressionUpdatable<T> expressionObjectUpdatable) {
        this.expressionObjectUpdatable = expressionObjectUpdatable;
    }

    @Override
    public ClientExpressionUpdatable<T> getClientUpdate() {
        return expressionObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return expressionObjectUpdatable.executeRows();
    }

    @Override
    public ExpressionUpdatable<T> withVersion(boolean condition, Object versionValue) {
        if (condition) {
            expressionObjectUpdatable.withVersion(versionValue);
        }
        return this;
    }
    @Override
    public ExpressionUpdatable<T> asTable(Function<String, String> tableNameAs) {
        expressionObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> asSchema(Function<String, String> schemaAs) {
        expressionObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> asAlias(String alias) {
        expressionObjectUpdatable.asAlias(alias);
        return this;
    }
    @Override
    public ExpressionUpdatable<T> noInterceptor() {
        expressionObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public ExpressionUpdatable<T> useInterceptor(String name) {
        expressionObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> noInterceptor(String name) {
        expressionObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public ExpressionUpdatable<T> useInterceptor() {
        expressionObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public ExpressionUpdatable<T> useLogicDelete(boolean enable) {
        expressionObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        expressionObjectUpdatable.executeRows(expectRows, msg, code);
    }
}
