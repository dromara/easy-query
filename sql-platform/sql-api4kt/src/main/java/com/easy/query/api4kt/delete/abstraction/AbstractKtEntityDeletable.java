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
    private final ClientEntityDeletable<T> entityObjectDeletable;

    public AbstractKtEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        this.entityObjectDeletable = entityObjectDeletable;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return entityObjectDeletable.getExpressionContext();
    }

    @Override
    public String toSQL() {
        return entityObjectDeletable.toSQL();
    }

    @Override
    public long executeRows() {
        return entityObjectDeletable.executeRows();
    }


    @Override
    public KtEntityDeletable<T> useLogicDelete(boolean enable) {
        entityObjectDeletable.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtEntityDeletable<T> allowDeleteStatement(boolean allow) {
        entityObjectDeletable.allowDeleteStatement(allow);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asTable(Function<String, String> tableNameAs) {
        entityObjectDeletable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asSchema(Function<String, String> schemaAs) {
        entityObjectDeletable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtEntityDeletable<T> asAlias(String alias) {
        entityObjectDeletable.asAlias(alias);
        return this;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return entityObjectDeletable.toSQL(toSQLContext);
    }

    @Override
    public KtEntityDeletable<T> noInterceptor() {
        entityObjectDeletable.noInterceptor();
        return this;
    }

    @Override
    public KtEntityDeletable<T> useInterceptor(String name) {
        entityObjectDeletable.useInterceptor(name);
        return this;
    }

    @Override
    public KtEntityDeletable<T> noInterceptor(String name) {
        entityObjectDeletable.noInterceptor(name);
        return this;
    }

    @Override
    public KtEntityDeletable<T> useInterceptor() {
        entityObjectDeletable.useInterceptor();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectDeletable.executeRows(expectRows, msg, code);
    }
}
