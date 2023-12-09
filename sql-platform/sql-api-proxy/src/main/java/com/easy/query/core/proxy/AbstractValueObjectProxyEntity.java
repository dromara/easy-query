package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/11/7 20:07
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractValueObjectProxyEntity<TProxy, TProperty> implements SQLColumn<TProxy, TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String parentProperty;

    public AbstractValueObjectProxyEntity(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.entitySQLContext = entitySQLContext;

        this.table = table;
        this.parentProperty = property;
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

    protected <TVProperty> SQLColumn<TProxy, TVProperty> get(String property) {
        return new SQLColumnImpl<>(entitySQLContext,table, getValueProperty(property));
    }

    protected String getValueProperty(String property) {
        return parentProperty + "." + property;
    }
}
