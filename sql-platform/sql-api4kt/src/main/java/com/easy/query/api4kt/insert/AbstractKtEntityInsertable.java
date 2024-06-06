package com.easy.query.api4kt.insert;

import com.easy.query.api4kt.sql.SQLKtColumnConfigurer;
import com.easy.query.api4kt.sql.SQLKtColumnOnlySelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnConfigurerImpl;
import com.easy.query.api4kt.sql.impl.SQLKtColumnOnlySelectorImpl;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import kotlin.reflect.KProperty1;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/6/2 16:15
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractKtEntityInsertable<T> implements KtEntityInsertable<T> {
    private final ClientInsertable<T> clientInsertable;

    public AbstractKtEntityInsertable(ClientInsertable<T> clientInsertable) {

        this.clientInsertable = clientInsertable;
    }

    @Override
    public KtEntityInsertable<T> insert(T entity) {
        clientInsertable.insert(entity);
        return this;
    }

    @Override
    public KtEntityInsertable<T> insert(Collection<T> entities) {
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
    public KtEntityInsertable<T> noInterceptor() {
        clientInsertable.noInterceptor();
        return this;
    }

    @Override
    public KtEntityInsertable<T> useInterceptor(String name) {
        clientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public KtEntityInsertable<T> noInterceptor(String name) {
        clientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public KtEntityInsertable<T> useInterceptor() {
        clientInsertable.useInterceptor();
        return this;
    }

    @Override
    public KtEntityInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientInsertable.setSQLStrategy(condition, sqlStrategy);
        return this;
    }

    @Override
    public KtEntityInsertable<T> asTable(Function<String, String> tableNameAs) {
        clientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtEntityInsertable<T> asSchema(Function<String, String> schemaAs) {
        clientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtEntityInsertable<T> asAlias(String alias) {
        clientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public KtEntityInsertable<T> onDuplicateKeyIgnore() {
        clientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public KtEntityInsertable<T> onDuplicateKeyUpdate() {
        clientInsertable.onDuplicateKeyUpdate();
        return this;
    }

    @Override
    public String toSQL(T entity) {
        return clientInsertable.toSQL(entity);
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return clientInsertable.toSQL(entity,toSQLContext);
    }
    @Override
    public KtEntityInsertable<T> onConflictDoUpdate() {
        clientInsertable.onConflictDoUpdate();
        return this;
    }

    @Override
    public KtEntityInsertable<T> onConflictDoUpdate(KProperty1<? super T, ?> constraintProperty) {
        clientInsertable.onConflictDoUpdate(EasyKtLambdaUtil.getPropertyName(constraintProperty));
        return this;
    }

    @Override
    public KtEntityInsertable<T> onConflictDoUpdate(KProperty1<? super T, ?> constraintProperty, SQLExpression1<SQLKtColumnOnlySelector<T>> setColumnSelector) {
        clientInsertable.onConflictDoUpdate(EasyKtLambdaUtil.getPropertyName(constraintProperty),setSelector->{
            setColumnSelector.apply(new SQLKtColumnOnlySelectorImpl<>(setSelector));
        });
        return this;
    }


    @Override
    public KtEntityInsertable<T> onDuplicateKeyUpdate(SQLExpression1<SQLKtColumnOnlySelector<T>> setColumnSelector) {
        clientInsertable.onDuplicateKeyUpdate(setSelector->{
            setColumnSelector.apply(new SQLKtColumnOnlySelectorImpl<>(setSelector));
        });
        return this;
    }

    @Override
    public KtEntityInsertable<T> batch(boolean use) {
        clientInsertable.batch(use);
        return this;
    }

    @Override
    public KtEntityInsertable<T> columnConfigure(SQLExpression1<SQLKtColumnConfigurer<T>> columnConfigureExpression) {
        clientInsertable.columnConfigure(configurer->{
            columnConfigureExpression.apply(new SQLKtColumnConfigurerImpl<>(configurer));
        });
        return this;
    }

    @Override
    public KtEntityInsertable<T> asTableLink(Function<String, String> linkAs) {
        clientInsertable.asTableLink(linkAs);
        return this;
    }
    @Override
    public KtEntityInsertable<T> behaviorConfigure(SQLExpression1<EasyBehavior> configure) {
        clientInsertable.behaviorConfigure(configure);
        return this;
    }
}
