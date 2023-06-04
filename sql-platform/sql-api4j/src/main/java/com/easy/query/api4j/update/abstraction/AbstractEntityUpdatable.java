package com.easy.query.api4j.update.abstraction;

import com.easy.query.api4j.sql.SQLColumnSelector;
import com.easy.query.api4j.sql.impl.SQLColumnSelectorImpl;
import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractEntityUpdatable<T> implements EntityUpdatable<T> {

    protected final ClientEntityUpdatable<T> entityObjectUpdatable;

    public AbstractEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        this.entityObjectUpdatable = entityObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return entityObjectUpdatable.executeRows();
    }

    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            entityObjectUpdatable.setColumns(selector -> {
                columnSelectorExpression.apply(new SQLColumnSelectorImpl<>(selector));
            });
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            entityObjectUpdatable.setColumns(selector -> {
                columnSelectorExpression.apply(new SQLColumnSelectorImpl<>(selector));
            });
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<SQLColumnSelector<T>> columnSelectorExpression) {
        if (condition) {
            entityObjectUpdatable.setColumns(selector -> {
                columnSelectorExpression.apply(new SQLColumnSelectorImpl<>(selector));
            });
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        entityObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asAlias(String alias) {
        entityObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public EntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityObjectUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return entityObjectUpdatable.toSQL(entity);
    }

    @Override
    public EntityUpdatable<T> noInterceptor() {
        entityObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor(String name) {
        entityObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor(String name) {
        entityObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor() {
        entityObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useLogicDelete(boolean enable) {
        entityObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectUpdatable.executeRows(expectRows, msg, code);
    }
}
