package com.easy.query.core.proxy;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.SQLAnyColumn;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.columns.SQLNumberColumn;
import com.easy.query.core.proxy.columns.SQLStringColumn;
import com.easy.query.core.proxy.columns.impl.SQLAnyColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLBooleanColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLDateTimeColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNavigateColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLNumberColumnImpl;
import com.easy.query.core.proxy.columns.impl.SQLStringColumnImpl;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLByteTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLDoubleTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLFloatTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLocalTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLLongTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLShortTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLTimestampTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUUIDTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLUtilDateTypeColumn;
import com.easy.query.core.proxy.columns.types.impl.SQLAnyTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBigDecimalTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLBooleanTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLByteTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLDoubleTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLFloatTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLIntegerTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalDateTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLocalTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLLongTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLShortTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLStringTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimeTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLTimestampTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUUIDTypeColumnImpl;
import com.easy.query.core.proxy.columns.types.impl.SQLUtilDateTypeColumnImpl;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/11/7 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractValueObjectProxyEntity<TProxy, TProperty> implements SQLColumn<TProxy, TProperty>, ValueObjectProxyEntity {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String parentProperty;
    private Class<?> propType;

    public AbstractValueObjectProxyEntity(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.parentProperty = property;
        this.propType = Object.class;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getValue() {
        return parentProperty;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    protected <T, N> N __cast(T original){
        return EasyObjectUtil.typeCastNullable(original);
    }
    protected <TPropertyProxy extends SQLColumn<TProxy, TVProperty>, TVProperty> TPropertyProxy getValueObject(TPropertyProxy propertyProxy) {
        return propertyProxy;
    }

    protected <TProp> SQLColumn<TProxy, TProp> get(String property, Class<TProp> propType) {
        return new SQLColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }

    protected <TProp> SQLDateTimeColumn<TProxy, TProp> getDateTimeColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLDateTimeColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }
    protected <TProp> SQLNumberColumn<TProxy, TProp> getNumberColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLNumberColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }
    protected <TProp> SQLStringColumn<TProxy, TProp> getStringColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLStringColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }
    protected <TProp> SQLBooleanColumn<TProxy, TProp> getBooleanColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLBooleanColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }
    protected <TProp> SQLAnyColumn<TProxy, TProp> getAnyColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLAnyColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }
    protected String getValueProperty(String property) {
        return parentProperty + "." + property;
    }

    protected <TProp> SQLNavigateColumn<TProxy, TProp> getNavigate(String property, Class<TProp> propType) {
        return new SQLNavigateColumnImpl<>(entitySQLContext, table, property, propType);
    }



    protected <TProp> SQLAnyTypeColumn<TProxy,TProp> getAnyTypeColumn(String property, @Nullable Class<TProp> propType) {
        return new SQLAnyTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property),propType);
    }

    protected SQLBigDecimalTypeColumn<TProxy> getBigDecimalTypeColumn(String property) {
        return new SQLBigDecimalTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLBooleanTypeColumn<TProxy> getBooleanTypeColumn(String property) {
        return new SQLBooleanTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLByteTypeColumn<TProxy> getByteTypeColumn(String property) {
        return new SQLByteTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLDateTypeColumn<TProxy> getSQLDateTypeColumn(String property) {
        return new SQLDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLDoubleTypeColumn<TProxy> getDoubleTypeColumn(String property) {
        return new SQLDoubleTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLFloatTypeColumn<TProxy> getFloatTypeColumn(String property) {
        return new SQLFloatTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLIntegerTypeColumn<TProxy> getIntegerTypeColumn(String property) {
        return new SQLIntegerTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLLocalDateTimeTypeColumn<TProxy> getLocalDateTimeTypeColumn(String property) {
        return new SQLLocalDateTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLLocalDateTypeColumn<TProxy> getLocalDateTypeColumn(String property) {
        return new SQLLocalDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLLocalTimeTypeColumn<TProxy> getLocalTimeTypeColumn(String property) {
        return new SQLLocalTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLLongTypeColumn<TProxy> getLongTypeColumn(String property) {
        return new SQLLongTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLShortTypeColumn<TProxy> getShortTypeColumn(String property) {
        return new SQLShortTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }

    protected SQLStringTypeColumn<TProxy> getStringTypeColumn(String property) {
        return new SQLStringTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLTimestampTypeColumn<TProxy> getTimestampTypeColumn(String property) {
        return new SQLTimestampTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLTimeTypeColumn<TProxy> getTimeTypeColumn(String property) {
        return new SQLTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLUtilDateTypeColumn<TProxy> getUtilDateTypeColumn(String property) {
        return new SQLUtilDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }
    protected SQLUUIDTypeColumn<TProxy> getUUIDTypeColumn(String property) {
        return new SQLUUIDTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType = clazz;
    }

    @Override
    public <TR> SQLColumn<TProxy, TR> setPropertyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
