package com.easy.query.core.proxy.impl;

import com.easy.query.core.basic.extension.conversion.ColumnFunctionReaderImpl;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.PropValueConvertColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.function.Function;

/**
 * create time 2025/6/12 21:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropValueConvertColumnImpl<TRProperty, TDBProperty> implements PropValueConvertColumn<TRProperty, TDBProperty> {
    private final PropTypeColumn<TDBProperty> propTypeColumn;
    private final Function<TDBProperty,TRProperty> valueConverter;

    public PropValueConvertColumnImpl(PropTypeColumn<TDBProperty> propTypeColumn, Function<TDBProperty,TRProperty> converter) {
        this.propTypeColumn = propTypeColumn;
        this.valueConverter = converter;
    }

    @Override
    public Function<TDBProperty,TRProperty> getValueConverter() {
        return valueConverter;
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
        if (propTypeColumn instanceof SQLColumn) {
            return PropValueConvertColumn.super.as(propertyAlias);
        } else {
            JdbcTypeHandler jdbcTypeHandler = getEntitySQLContext().getRuntimeContext().getJdbcTypeHandlerManager().getHandler(getPropertyType());
            ColumnFunctionReaderImpl columnReader = new ColumnFunctionReaderImpl(getPropertyType(),jdbcTypeHandler, valueConverter);
            getEntitySQLContext().getExpressionContext().getResultValueConverterMap(true).put(propertyAlias,columnReader);
            return propTypeColumn.as(propertyAlias);
        }
    }
}
