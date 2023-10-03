package com.easy.query.api4j.update.map;

import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/10/3 18:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMapUpdatable implements MapUpdatable<Map<String,Object>> {
    private final MapClientUpdatable<Map<String, Object>> mapMapClientUpdatable;

    public EasyMapUpdatable(MapClientUpdatable<Map<String,Object>> mapMapClientUpdatable){

        this.mapMapClientUpdatable = mapMapClientUpdatable;
    }

    @Override
    public MapUpdatable<Map<String, Object>> noInterceptor() {
        mapMapClientUpdatable.noInterceptor();
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> useInterceptor(String name) {
        mapMapClientUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> noInterceptor(String name) {
        mapMapClientUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> useInterceptor() {
        mapMapClientUpdatable.useInterceptor();
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> useLogicDelete(boolean enable) {
        mapMapClientUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> batch(boolean use) {
        mapMapClientUpdatable.batch(use);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        mapMapClientUpdatable.executeRows(expectRows,msg,code);
    }

    @Override
    public long executeRows() {
        return mapMapClientUpdatable.executeRows();
    }

    @Override
    public MapUpdatable<Map<String, Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        mapMapClientUpdatable.setSQLStrategy(condition,sqlStrategy);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> asTable(Function<String, String> tableNameAs) {
        mapMapClientUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> asSchema(Function<String, String> schemaAs) {
        mapMapClientUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> asAlias(String alias) {
        mapMapClientUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public MapUpdatable<Map<String, Object>> asTableLink(Function<String, String> linkAs) {
        mapMapClientUpdatable.asTableLink(linkAs);
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return mapMapClientUpdatable.getUpdateExpressionBuilder();
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> getMapClientUpdate() {
        return mapMapClientUpdatable;
    }

    @Override
    public MapUpdatable<Map<String, Object>> whereColumns(String... columnNames) {
        mapMapClientUpdatable.whereColumns(columnNames);
        return this;
    }
}
