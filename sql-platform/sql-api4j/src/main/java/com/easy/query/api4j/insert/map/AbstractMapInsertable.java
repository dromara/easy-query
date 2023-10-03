package com.easy.query.api4j.insert.map;

import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/10/3 09:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class AbstractMapInsertable implements MapInsertable<Map<String, Object>> {
    private final MapClientInsertable<Map<String, Object>> mapClientInsertable;

    public AbstractMapInsertable(MapClientInsertable<Map<String, Object>> mapClientInsertable) {

        this.mapClientInsertable = mapClientInsertable;
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return mapClientInsertable.getEntityInsertExpressionBuilder();
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return mapClientInsertable.executeRows(fillAutoIncrement);
    }

    @Override
    public String toSQL(Map<String, Object> entity) {
        return mapClientInsertable.toSQL(entity);
    }

    @Override
    public String toSQL(Map<String, Object> entity, ToSQLContext toSQLContext) {
        return mapClientInsertable.toSQL(entity, toSQLContext);
    }

    @Override
    public MapInsertable<Map<String, Object>> noInterceptor() {
        mapClientInsertable.noInterceptor();
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> useInterceptor(String name) {
        mapClientInsertable.useInterceptor(name);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> noInterceptor(String name) {
        mapClientInsertable.noInterceptor(name);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> useInterceptor() {
        mapClientInsertable.useInterceptor();
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> batch(boolean use) {
        mapClientInsertable.batch(use);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        mapClientInsertable.setSQLStrategy(condition,sqlStrategy);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> onDuplicateKeyIgnore() {
        mapClientInsertable.onDuplicateKeyIgnore();
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> asTable(Function<String, String> tableNameAs) {
        mapClientInsertable.asTable(tableNameAs);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> asSchema(Function<String, String> schemaAs) {
        mapClientInsertable.asSchema(schemaAs);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> asAlias(String alias) {
        mapClientInsertable.asAlias(alias);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> asTableLink(Function<String, String> linkAs) {
        mapClientInsertable.asTableLink(linkAs);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> insert(Map<String, Object> entity) {
        mapClientInsertable.insert(entity);
        return this;
    }

    @Override
    public MapInsertable<Map<String, Object>> insert(Collection<Map<String, Object>> entities) {
        mapClientInsertable.insert(entities);
        return this;
    }
}
