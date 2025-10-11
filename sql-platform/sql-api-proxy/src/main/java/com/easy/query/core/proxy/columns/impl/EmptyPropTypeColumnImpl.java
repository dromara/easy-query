package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

/**
 * create time 2025/10/11 11:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class EmptyPropTypeColumnImpl<T> implements PropTypeColumn<T> {
    @Override
    public <TR> void _setPropertyType(Class<TR> clazz) {

    }

    @Override
    public <TR> PropTypeColumn<TR> asAnyType(Class<TR> clazz) {
        return null;
    }

    @Override
    public Class<?> getPropertyType() {
        return null;
    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return null;
    }
}
