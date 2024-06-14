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
    private TProxy tProxy;

    public AbstractValueObjectProxyEntity(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.parentProperty = property;
        this.propType = Object.class;
    }

    @Override
    public void _setProxy(TProxy tProxy) {
        this.tProxy = tProxy;
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

    protected <T, N> N __cast(T original) {
        return EasyObjectUtil.typeCastNullable(original);
    }

    protected <TPropertyProxy extends SQLColumn<TProxy, TVProperty>, TVProperty> TPropertyProxy getValueObject(TPropertyProxy propertyProxy) {
        propertyProxy._setProxy(castChain());
        return propertyProxy;
    }

    protected <TProp> SQLColumn<TProxy, TProp> get(String property, Class<TProp> propType) {
        SQLColumn<TProxy, TProp> column = new SQLColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProp> SQLDateTimeColumn<TProxy, TProp> getDateTimeColumn(String property, @Nullable Class<TProp> propType) {
        SQLDateTimeColumn<TProxy, TProp> column =  new SQLDateTimeColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProp> SQLNumberColumn<TProxy, TProp> getNumberColumn(String property, @Nullable Class<TProp> propType) {
        SQLNumberColumn<TProxy, TProp> column = new SQLNumberColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProp> SQLStringColumn<TProxy, TProp> getStringColumn(String property, @Nullable Class<TProp> propType) {
        SQLStringColumn<TProxy, TProp> column =  new SQLStringColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProp> SQLBooleanColumn<TProxy, TProp> getBooleanColumn(String property, @Nullable Class<TProp> propType) {
        SQLBooleanColumn<TProxy, TProp> column = new SQLBooleanColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected <TProp> SQLAnyColumn<TProxy, TProp> getAnyColumn(String property, @Nullable Class<TProp> propType) {
        SQLAnyColumn<TProxy, TProp> column = new SQLAnyColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected String getValueProperty(String property) {
        return parentProperty + "." + property;
    }

    protected <TProp> SQLNavigateColumn<TProxy, TProp> getNavigate(String property, Class<TProp> propType) {
        SQLNavigateColumn<TProxy, TProp> column = new SQLNavigateColumnImpl<>(entitySQLContext, table, property, propType);
        column._setProxy(castChain());
        return column;
    }


    protected <TProp> SQLAnyTypeColumn<TProxy, TProp> getAnyTypeColumn(String property, @Nullable Class<TProp> propType) {
        SQLAnyTypeColumn<TProxy, TProp> column = new SQLAnyTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
        column._setProxy(castChain());
        return column;
    }

    protected SQLBigDecimalTypeColumn<TProxy> getBigDecimalTypeColumn(String property) {
        SQLBigDecimalTypeColumn<TProxy> column = new SQLBigDecimalTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLBooleanTypeColumn<TProxy> getBooleanTypeColumn(String property) {
        SQLBooleanTypeColumn<TProxy> column = new SQLBooleanTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLByteTypeColumn<TProxy> getByteTypeColumn(String property) {
        SQLByteTypeColumn<TProxy> column =  new SQLByteTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLDateTypeColumn<TProxy> getSQLDateTypeColumn(String property) {
        SQLDateTypeColumn<TProxy> column = new SQLDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLDoubleTypeColumn<TProxy> getDoubleTypeColumn(String property) {
        SQLDoubleTypeColumn<TProxy> column = new SQLDoubleTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLFloatTypeColumn<TProxy> getFloatTypeColumn(String property) {
        SQLFloatTypeColumn<TProxy> column = new SQLFloatTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLIntegerTypeColumn<TProxy> getIntegerTypeColumn(String property) {
        SQLIntegerTypeColumn<TProxy> column = new SQLIntegerTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLLocalDateTimeTypeColumn<TProxy> getLocalDateTimeTypeColumn(String property) {
        SQLLocalDateTimeTypeColumn<TProxy> column =  new SQLLocalDateTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLLocalDateTypeColumn<TProxy> getLocalDateTypeColumn(String property) {
        SQLLocalDateTypeColumn<TProxy> column = new SQLLocalDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLLocalTimeTypeColumn<TProxy> getLocalTimeTypeColumn(String property) {
        SQLLocalTimeTypeColumn<TProxy> column = new SQLLocalTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLLongTypeColumn<TProxy> getLongTypeColumn(String property) {
        SQLLongTypeColumn<TProxy> column = new SQLLongTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLShortTypeColumn<TProxy> getShortTypeColumn(String property) {
        SQLShortTypeColumn<TProxy> column = new SQLShortTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLStringTypeColumn<TProxy> getStringTypeColumn(String property) {
        SQLStringTypeColumn<TProxy> column = new SQLStringTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLTimestampTypeColumn<TProxy> getTimestampTypeColumn(String property) {
        SQLTimestampTypeColumn<TProxy> column =  new SQLTimestampTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLTimeTypeColumn<TProxy> getTimeTypeColumn(String property) {
        SQLTimeTypeColumn<TProxy> column = new SQLTimeTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLUtilDateTypeColumn<TProxy> getUtilDateTypeColumn(String property) {
        SQLUtilDateTypeColumn<TProxy> column = new SQLUtilDateTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
    }

    protected SQLUUIDTypeColumn<TProxy> getUUIDTypeColumn(String property) {
        SQLUUIDTypeColumn<TProxy> column = new SQLUUIDTypeColumnImpl<>(entitySQLContext, table, getValueProperty(property));
        column._setProxy(castChain());
        return column;
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
    public <TR> SQLColumn<TProxy, TR> asAnyType(Class<TR> clazz) {
        _setPropertyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Override
    public TProxy castChain() {
        return tProxy;
    }
}
