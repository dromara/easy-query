package com.easy.query.core.basic.api.insert;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEmptyInsertable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 08:49
 */
public class EasyEmptyClientInsertable<T> implements ClientInsertable<T> {
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;

    public EasyEmptyClientInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder) {

        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
    }

    @Override
    public ClientInsertable<T> insert(T entity) {
        if (entity == null) {
            return this;
        }
        SQLClientApiFactory sqlApiFactory = entityInsertExpressionBuilder.getRuntimeContext().getSQLClientApiFactory();
        ClientInsertable<T> insertable = sqlApiFactory.createInsertable((Class<T>) entity.getClass(), entityInsertExpressionBuilder);
        insertable.insert(entity);
        return insertable;
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public ClientInsertable<T> columnConfigure(SQLExpression1<ColumnConfigurer<T>> columnConfigureExpression) {
        return this;
    }

    @Override
    public ClientInsertable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public ClientInsertable<T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public ClientInsertable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public ClientInsertable<T> asTableLink(Function<String, String> linkAs) {
        return this;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return 0;
    }

    @Override
    public String toSQL(Object entity) {
        return null;
    }
    @Override
    public String toSQL(Object entity, ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public ClientInsertable<T> noInterceptor() {
        return this;
    }

    @Override
    public ClientInsertable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public ClientInsertable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public ClientInsertable<T> useInterceptor() {
        return this;
    }

    @Override
    public ClientInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyIgnore() {
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate() {
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(String constraintProperty) {
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(String constraintProperty, SQLExpression1<ColumnOnlySelector<T>> setColumnSelector) {
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(SQLExpression1<ColumnOnlySelector<T>> setColumnSelector) {
        return this;
    }

    @Override
    public ClientInsertable<T> batch(boolean use) {
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyUpdate() {
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyUpdate(SQLExpression1<ColumnOnlySelector<T>> setColumnSelector) {
        return this;
    }
}
