package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLBooleanTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLBooleanTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, Boolean> implements SQLBooleanTypeColumn<TProxy> {
    public SQLBooleanTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, Boolean.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<Boolean> getEntityClass() {
        return Boolean.class;
    }

    @Override
    public SQLBooleanTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLBooleanTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
