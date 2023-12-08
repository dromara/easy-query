package com.easy.query.core.proxy.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;

import java.util.Objects;

/**
 * create time 2023/6/22 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnImpl<TProxy, TProperty> implements SQLColumn<TProxy, TProperty> {
    private final EntitySQLContext entitySQLContext;
    private final TableAvailable table;
    private final String property;

    public SQLColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        this.entitySQLContext = entitySQLContext;
        this.table = table;
        this.property = property;
    }

    @Override
    public TableAvailable getTable() {
        Objects.requireNonNull(table, "cant found table in sql context");
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
}
