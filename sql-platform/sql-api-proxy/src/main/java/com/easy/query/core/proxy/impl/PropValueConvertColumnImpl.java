package com.easy.query.core.proxy.impl;

import com.easy.query.core.basic.extension.conversion.ColumnFunctionReaderImpl;
import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.extension.conversion.ColumnReaderImpl;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.ValueConvertFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.PropValueConvertColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.Map;
import java.util.function.Function;

/**
 * create time 2025/6/12 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropValueConvertColumnImpl<TRProperty, TDBProperty> implements PropValueConvertColumn<TRProperty> {
    private final PropTypeColumn<TDBProperty> propTypeColumn;
    private final ValueConvertFunction<TDBProperty, TRProperty> valueConverter;

    public PropValueConvertColumnImpl(PropTypeColumn<TDBProperty> propTypeColumn, ValueConvertFunction<TDBProperty, TRProperty> converter) {
        this.propTypeColumn = propTypeColumn;
        this.valueConverter = converter;
    }
    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propTypeColumn._setPropertyType(clazz);
    }

    @Override
    public <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz) {
        return propTypeColumn.asAnyType(clazz);
    }

    @Override
    public Class<?> getPropertyType() {
        return propTypeColumn.getPropertyType();
    }

    @Override
    public String getValue() {
        return propTypeColumn.getValue();
    }

    @Override
    public TableAvailable getTable() {
        return propTypeColumn.getTable();
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return propTypeColumn.getEntitySQLContext();
    }

    @Override
    public SQLSelectAsExpression as(String propertyAlias) {

        ExpressionContext expressionContext = getEntitySQLContext().getExpressionContext();
        Map<String, ColumnReader> resultValueConverterMap = getEntitySQLContext().getExpressionContext().getResultValueConverterMap(true);
        if (propTypeColumn instanceof SQLColumn) {
            TableAvailable table = propTypeColumn.getTable();
            ColumnMetadata columnMetadata = propTypeColumn.getTable().getEntityMetadata().getColumnNotNull(propTypeColumn.getValue());
            ColumnReaderImpl columnReader = new ColumnReaderImpl(table.getEntityMetadata(), columnMetadata, valueConverter);
            String key = getPropertyAlias(propertyAlias, columnMetadata.getPropertyName());
            expressionContext.getResultValueConverterMap(true).put(key, columnReader);
            return PropValueConvertColumn.super.as(propertyAlias);
        } else {
            JdbcTypeHandler jdbcTypeHandler = getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager().getHandler(getPropertyType());
            ColumnFunctionReaderImpl columnReader = new ColumnFunctionReaderImpl(getPropertyType(), jdbcTypeHandler, valueConverter);
            String key = getPropertyAlias(propertyAlias, propTypeColumn.getValue());
            resultValueConverterMap.put(key, columnReader);
            return propTypeColumn.as(propertyAlias);
        }
    }

    private String getPropertyAlias(String propertyAlias, String def) {
        if (propertyAlias == null) {
            if (def != null) {
                return def;
            }
        }
        return propertyAlias;
    }
}
