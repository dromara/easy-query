package com.easy.query.api4j.insert;

import com.easy.query.api4j.sql.SQLColumnConfigurer;
import com.easy.query.api4j.sql.SQLColumnOnlySelector;
import com.easy.query.api4j.sql.impl.SQLColumnConfigurerImpl;
import com.easy.query.api4j.sql.impl.SQLColumnOnlySelectorImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/6/2 16:15
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityInsertable<T> implements EntityInsertable<T> {
    private final ClientInsertable<T> clientInsertable;

    public AbstractEntityInsertable(ClientInsertable<T> clientInsertable) {

        this.clientInsertable = clientInsertable;
    }

    @Override
    public EntityInsertable<T> insert(T entity) {
        clientInsertable.insert(entity);
        return this;
    }

    @Override
    public EntityInsertable<T> insert(Collection<T> entities) {
        clientInsertable.insert(entities);
        return this;
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return clientInsertable.getEntityInsertExpressionBuilder();
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return clientInsertable.executeRows(fillAutoIncrement);
    }

    @Override
    public EntityInsertable<T> noInterceptor() {
        clientInsertable.noInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<T> useInterceptor(String name) {
        clientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<T> noInterceptor(String name) {
        clientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<T> useInterceptor() {
        clientInsertable.useInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientInsertable.setSQLStrategy(condition, sqlStrategy);
        return this;
    }

    @Override
    public EntityInsertable<T> asTable(Function<String, String> tableNameAs) {
        clientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityInsertable<T> asSchema(Function<String, String> schemaAs) {
        clientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityInsertable<T> asAlias(String alias) {
        clientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public EntityInsertable<T> onDuplicateKeyIgnore() {
        clientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public EntityInsertable<T> onDuplicateKeyUpdate() {
        clientInsertable.onDuplicateKeyUpdate();
        return this;
    }

    @Override
    public String toSQL(T entity) {
        return clientInsertable.toSQL(entity);
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return clientInsertable.toSQL(entity, toSQLContext);
    }

    @Override
    public EntityInsertable<T> onConflictDoUpdate() {
        clientInsertable.onConflictDoUpdate();
        return this;
    }

    @Override
    public EntityInsertable<T> onConflictDoUpdate(Property<T, ?> constraintProperty) {
        clientInsertable.onConflictDoUpdate(EasyLambdaUtil.getPropertyName(constraintProperty));
        return this;
    }

    @Override
    public EntityInsertable<T> onConflictDoUpdate(Property<T, ?> constraintProperty, SQLExpression1<SQLColumnOnlySelector<T>> setColumnSelector) {
        clientInsertable.onConflictDoUpdate(EasyLambdaUtil.getPropertyName(constraintProperty), setSelector -> {
            setColumnSelector.apply(new SQLColumnOnlySelectorImpl<>(setSelector));
        });
        return this;
    }


    @Override
    public EntityInsertable<T> onDuplicateKeyUpdate(SQLExpression1<SQLColumnOnlySelector<T>> setColumnSelector) {
        clientInsertable.onDuplicateKeyUpdate(setSelector -> {
            setColumnSelector.apply(new SQLColumnOnlySelectorImpl<>(setSelector));
        });
        return this;
    }

    @Override
    public EntityInsertable<T> batch(boolean use) {
        clientInsertable.batch(use);
        return this;
    }

    @Override
    public EntityInsertable<T> columnConfigure(SQLExpression1<SQLColumnConfigurer<T>> columnConfigureExpression) {
        clientInsertable.columnConfigure(configurer -> {
            columnConfigureExpression.apply(new SQLColumnConfigurerImpl<>(configurer));
        });
        return this;
    }

    @Override
    public EntityInsertable<T> asTableLink(Function<String, String> linkAs) {
        clientInsertable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityInsertable<T> behaviorConfigure(SQLExpression1<EasyBehavior> configure) {
        clientInsertable.behaviorConfigure(configure);
        return this;
    }
}
