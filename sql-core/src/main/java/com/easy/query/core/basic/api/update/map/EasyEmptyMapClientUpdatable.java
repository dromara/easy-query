package com.easy.query.core.basic.api.update.map;

import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/10/3 17:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyMapClientUpdatable implements MapClientUpdatable<Map<String, Object>> {
    @Override
    public MapClientUpdatable<Map<String, Object>> noInterceptor() {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> useInterceptor(String name) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> noInterceptor(String name) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> useInterceptor() {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> batch(boolean use) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {

    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asAlias(String alias) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> asTableLink(Function<String, String> linkAs) {
        return this;
    }
    @Override
    public MapClientUpdatable<Map<String, Object>> configure(SQLExpression1<ContextConfigurer> configurer) {
        return this;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> whereColumns(String... columnNames) {
        return this;
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> columnConfigure(SQLExpression1<ColumnConfigurer<Map<String, Object>>> columnConfigureExpression) {
        return this;
    }
}
