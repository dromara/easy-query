package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.AssertExceptionFactory;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * create time 2023/6/2 12:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyClientEntityUpdatable<T> implements ClientEntityUpdatable<T> {
    private final ServiceProvider serviceProvider;

    public EasyEmptyClientEntityUpdatable(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }
    @Override
    public ClientEntityUpdatable<T> noInterceptor() {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useInterceptor() {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            AssertExceptionFactory assertExceptionFactory = serviceProvider.getService(AssertExceptionFactory.class);
            throw assertExceptionFactory.createExecuteConcurrentException(expectRows, rows, msg, code);
        }

    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public ClientEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTableLink(Function<String, String> linkAs) {
        return this;
    }
    @Override
    public ClientEntityUpdatable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        return this;
    }

    @Override
    public List<T> getEntities() {
        return EasyCollectionUtil.emptyList();
    }

    @Override
    public ClientEntityUpdatable<T> setColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> whereColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> columnConfigure(SQLActionExpression1<ColumnConfigurer<T>> columnConfigureExpression) {
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return null;
    }

    @Override
    public String toSQL(Object entity, ToSQLContext toSQLContext) {
        return "";
    }

    @Override
    public ClientEntityUpdatable<T> batch(boolean use) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> ignoreVersion(boolean ignored) {
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        throw new NullPointerException("empty entity can not get EntityUpdateExpressionBuilder");
    }

    @Override
    public ClientEntityUpdatable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        return this;
    }
}
