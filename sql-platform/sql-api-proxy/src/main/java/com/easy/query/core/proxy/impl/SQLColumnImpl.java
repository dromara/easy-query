package com.easy.query.core.proxy.impl;

import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyNavValueAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2023/6/22 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnImpl<TProxy, TProperty> implements SQLColumn<TProxy, TProperty>, ProxyNavValueAvailable {
    protected TProxy tProxy;
    protected final EntitySQLContext entitySQLContext;
    protected final TableAvailable table;
    protected final String property;
    protected Class<?> propType;
    private String navValue;

    public SQLColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, @Nullable Class<TProperty> propType) {
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
        this.propType = propType;
    }

    @Override
    public TableAvailable getTable() {
//        Objects.requireNonNull(table, "cant found table in sql context");
        return table;
    }

    @Override
    public String getValue() {
        return property;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public @Nullable Class<?> getPropertyType() {
        return propType;
    }

    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {
        this.propType = clazz;
    }


    @Override
    public TProxy castChain() {
        return tProxy;
    }


    @Override
    public void _setProxy(TProxy tProxy) {
        this.tProxy = tProxy;
    }

    @Override
    public String getNavValue() {
        return this.navValue;
    }

    @Override
    public void setNavValue(String val) {
        this.navValue = val;
    }
}
