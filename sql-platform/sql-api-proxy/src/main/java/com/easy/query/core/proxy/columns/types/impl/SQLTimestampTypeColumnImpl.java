package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLTimestampTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.sql.Timestamp;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLTimestampTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, Timestamp> implements SQLTimestampTypeColumn<TProxy> {
    public SQLTimestampTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, Timestamp.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<Timestamp> getEntityClass() {
        return Timestamp.class;
    }

    @Override
    public SQLTimestampTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLTimestampTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
