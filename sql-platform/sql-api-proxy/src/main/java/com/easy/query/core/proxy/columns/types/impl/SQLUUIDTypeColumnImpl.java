package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLUUIDTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.util.UUID;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLUUIDTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, UUID> implements SQLUUIDTypeColumn<TProxy> {
    public SQLUUIDTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, UUID.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<UUID> getEntityClass() {
        return UUID.class;
    }

    @Override
    public SQLUUIDTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLUUIDTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
