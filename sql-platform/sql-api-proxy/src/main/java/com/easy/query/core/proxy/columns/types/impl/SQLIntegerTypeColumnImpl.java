package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLIntegerTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, Integer> implements SQLIntegerTypeColumn<TProxy> {
    public SQLIntegerTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, Integer.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<Integer> getEntityClass() {
        return Integer.class;
    }

    @Override
    public SQLIntegerTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLIntegerTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
