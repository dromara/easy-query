package com.easy.query.api4kt.delete.abstraction;

import com.easy.query.api4kt.delete.KtEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractEntityDelete.java
 * @Description: 文件说明
 * @Date: 2023/2/28 12:33
 */
public abstract class AbstractKtEntityDeletable<T> implements KtEntityDeletable<T> {
    private final ClientEntityDeletable<T> clientEntityDeletable;

    public AbstractKtEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        this.clientEntityDeletable = entityObjectDeletable;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return clientEntityDeletable.getExpressionContext();
    }

    @Override
    public String toSQL() {
        return clientEntityDeletable.toSQL();
    }

    @Override
    public long executeRows() {
        return clientEntityDeletable.executeRows();
    }


    @Override
    public KtEntityDeletable<T> useLogicDelete(boolean enable) {
        clientEntityDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtEntityDeletable<T> allowDeleteStatement(boolean allow) {
        clientEntityDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        clientEntityDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asSchema(Function<String, String> schemaAs) {
        clientEntityDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asAlias(String alias) {
        clientEntityDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return clientEntityDeletable.toSQL(toSQLContext);
    }

    @Override
    public KtEntityDeletable<T> noInterceptor() {
        clientEntityDeletable.noInterceptor();
        return this;
    }

    @Override
    public KtEntityDeletable<T> useInterceptor(String name) {
        clientEntityDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public KtEntityDeletable<T> noInterceptor(String name) {
        clientEntityDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public KtEntityDeletable<T> useInterceptor() {
        clientEntityDeletable.useInterceptor();
        return this;
    }

    @Override
    public KtEntityDeletable<T> noVersionError() {
        clientEntityDeletable.noVersionError();
        return this;
    }

    @Override
    public KtEntityDeletable<T> noVersionIgnore() {
        clientEntityDeletable.noVersionIgnore();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        clientEntityDeletable.executeRows(expectRows, msg, code);
    }
}
