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
