package com.easy.query.api.proxy.entity.insert;

import com.easy.query.api.proxy.sql.ProxyColumnConfigurer;
import com.easy.query.api.proxy.sql.impl.ProxyColumnConfigurerImpl;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.builder.impl.FetchSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/12/7 13:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class AbstractEntityInsertable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityInsertable<TProxy, T> {
    private final TProxy tProxy;
    private final ClientInsertable<T> clientInsertable;

    public AbstractEntityInsertable(TProxy tProxy, ClientInsertable<T> clientInsertable) {
        this.clientInsertable = clientInsertable;
        this.tProxy = tProxy.create(clientInsertable.getEntityInsertExpressionBuilder().getTable(0).getEntityTable(),clientInsertable.getEntityInsertExpressionBuilder(), getEntityInsertExpressionBuilder().getRuntimeContext());

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
    public String toSQL(T entity) {
        return clientInsertable.toSQL(entity);
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return clientInsertable.toSQL(entity, toSQLContext);
    }

    @Override
    public EntityInsertable<TProxy, T> noInterceptor() {
        clientInsertable.noInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> useInterceptor(String name) {
        clientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> noInterceptor(String name) {
        clientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> useInterceptor() {
        clientInsertable.useInterceptor();
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> batch(boolean use) {
        clientInsertable.batch(use);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        clientInsertable.setSQLStrategy(condition,sqlStrategy);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyIgnore() {
        clientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asTable(Function<String, String> tableNameAs) {
        clientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asSchema(Function<String, String> schemaAs) {
        clientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asAlias(String alias) {
        clientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> asTableLink(Function<String, String> linkAs) {
        clientInsertable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> insert(T entity) {
        clientInsertable.insert(entity);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> insert(Collection<T> entities) {
        clientInsertable.insert(entities);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> columnConfigure(SQLExpression1<ProxyColumnConfigurer<TProxy, T>> columnConfigureExpression) {
        clientInsertable.columnConfigure(c->{
            columnConfigureExpression.apply(new ProxyColumnConfigurerImpl<>(c.getConfigurer()));
        });
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictThen(SQLFuncExpression1<TProxy, SQLSelectExpression> updateSetSelector, SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertySelector) {
        Collection<String> constraintProperties = parseConstraintProperties(constraintPropertySelector);
        clientInsertable.onConflictThen(o->{
            if(updateSetSelector!=null){
                SQLSelectExpression sqlSelectExpression = updateSetSelector.apply(tProxy);
                if(sqlSelectExpression!=null){
                    sqlSelectExpression.accept(o.getOnlySelector());
                }
            }
        },constraintProperties);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate() {
        clientInsertable.onConflictDoUpdate();
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertyExpression) {
        Collection<String> constraintProperties = parseConstraintProperties(constraintPropertyExpression);
        clientInsertable.onConflictDoUpdate(constraintProperties);
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onConflictDoUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertyExpression, SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression) {
        Collection<String> constraintProperties = parseConstraintProperties(constraintPropertyExpression);
        clientInsertable.onConflictDoUpdate(constraintProperties,s->{
            SQLSelectExpression sqlSelectExpression = updatePropertyExpression.apply(tProxy);
            sqlSelectExpression.accept(s.getOnlySelector());
        });
        return this;
    }
    private Collection<String> parseConstraintProperties(SQLFuncExpression1<TProxy, SQLSelectExpression> constraintPropertySelector){
        if(constraintPropertySelector!=null){
            SQLSelectExpression columnExpression = constraintPropertySelector.apply(tProxy);
            if(columnExpression!=null){
                ArrayList<String> properties = new ArrayList<>();
                FetchSelector fetchSelector = new FetchSelector(properties);
                columnExpression.accept(fetchSelector);
                return properties;
            }
        }
        return null;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyUpdate() {
        clientInsertable.onDuplicateKeyUpdate();
        return this;
    }

    @Override
    public EntityInsertable<TProxy, T> onDuplicateKeyUpdate(SQLFuncExpression1<TProxy, SQLSelectExpression> updatePropertyExpression) {
        clientInsertable.onDuplicateKeyUpdate(s->{
            SQLSelectExpression sqlSelectExpression = updatePropertyExpression.apply(tProxy);
            sqlSelectExpression.accept(s.getOnlySelector());
        });
        return this;
    }
}
