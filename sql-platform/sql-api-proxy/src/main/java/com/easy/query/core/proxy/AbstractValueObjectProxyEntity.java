package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
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

    protected <TVProperty> SQLColumn<TProxy, TVProperty> get(String property, Class<TVProperty> propType) {
        return new SQLColumnImpl<>(entitySQLContext, table, getValueProperty(property), propType);
    }

    protected String getValueProperty(String property) {
        return parentProperty + "." + property;
    }

    @Override
    public Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public <TR> PropTypeColumn<TR> castType(Class<TR> clazz) {
        this.propType = clazz;
        return EasyObjectUtil.typeCastNullable(this);
    }
}
