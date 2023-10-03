package com.easy.query.core.basic.api.insert.map;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/10/2 16:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyMapClientInsertable implements MapClientInsertable<Map<String,Object>>{
    private final EntityInsertExpressionBuilder entityInsertExpressionBuilder;

    public EasyEmptyMapClientInsertable(EntityInsertExpressionBuilder entityInsertExpressionBuilder){

        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
    }
    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        return 0;
    }

    @Override
    public String toSQL(Map<String, Object> entity) {
        return null;
    }

    @Override
    public String toSQL(Map<String, Object> entity, ToSQLContext toSQLContext) {
        return null;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> insert(Map<String, Object> map) {
        if (map == null) {
            return this;
        }
        SQLClientApiFactory sqlApiFactory = entityInsertExpressionBuilder.getRuntimeContext().getSQLClientApiFactory();
        MapClientInsertable<Map<String, Object>> insertable = sqlApiFactory.createMapInsertable(entityInsertExpressionBuilder);
        insertable.insert(map);
        return insertable;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> noInterceptor() {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> useInterceptor(String name) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> noInterceptor(String name) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> useInterceptor() {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> batch(boolean use) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> onDuplicateKeyIgnore() {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> asAlias(String alias) {
        return this;
    }

    @Override
    public MapClientInsertable<Map<String, Object>> asTableLink(Function<String, String> linkAs) {
        return this;
    }
}
