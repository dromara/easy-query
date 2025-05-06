package com.easy.query.api.proxy.entity.insert;

import com.easy.query.api.proxy.entity.insert.extension.ProxyColumnConfigurer;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/12/7 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyEntityInsertable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityInsertable<TProxy, T> {

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return null;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return 0;
    }

    @Override
    public String toSQL(T entity) {
        return null;
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public List<T> getEntities() {
        return EasyCollectionUtil.emptyList();
    }

    @Override
    public EntityInsertable<TProxy, T> noInterceptor() {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> useInterceptor(String name) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> noInterceptor(String name) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> useInterceptor() {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> batch(boolean use) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyIgnore() {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asAlias(String alias) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        return this;
    }
    @Override
    public EntityInsertable<TProxy, T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> insert(T entity) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> insert(Collection<T> entities) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> columnConfigure(SQLExpression2<TProxy, ProxyColumnConfigurer<TProxy, T>> columnConfigureExpression) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictThen(SQLFuncExpression1<TProxy, SQLSelectExpression> updateSetSelector, SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertySelector) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate() {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertyExpression) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertyExpression, SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyUpdate() {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression) {
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> configure(SQLExpression1<ContextConfigurer> configurer) {
        return this;
    }
}
