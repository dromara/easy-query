package com.easy.query.core.basic.api.insert;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: EasyEmptyInsertable.java
 * @Description: 文件说明
 * create time 2023/3/6 08:49
 */
public class EasyEmptyClientInsertable<T> implements ClientInsertable<T> {
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;

    public EasyEmptyClientInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder) {

        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
    }

    @Override
    public List<T> getEntities() {
        return EasyCollectionUtil.emptyList();
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
    public ClientInsertable<T> columnConfigure(SQLActionExpression1<ColumnConfigurer<T>> columnConfigureExpression) {
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
    public ClientInsertable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        return this;
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
    public ClientInsertable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        return this;
    }

    @Override
    public ClientInsertable<T> batch(boolean use) {
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictThen(SQLActionExpression1<ColumnOnlySelector<T>> updateSetSelector, Collection<String> constraintProperties) {
        return this;
    }
}
