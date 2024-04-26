package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLUtilDateTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.util.Date;


/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLUtilDateTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, Date> implements SQLUtilDateTypeColumn<TProxy> {
    public SQLUtilDateTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, Date.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<Date> getEntityClass() {
        return Date.class;
    }

    @Override
    public SQLUtilDateTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLUtilDateTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
