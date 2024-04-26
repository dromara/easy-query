package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLStringTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, String> implements SQLStringTypeColumn<TProxy> {
    public SQLStringTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, String.class);
    }
    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    public SQLStringTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLStringTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
