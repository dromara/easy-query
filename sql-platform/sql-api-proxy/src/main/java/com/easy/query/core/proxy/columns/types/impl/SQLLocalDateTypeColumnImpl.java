package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.time.LocalDate;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLLocalDateTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, LocalDate> implements SQLLocalDateTypeColumn<TProxy> {
    public SQLLocalDateTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, LocalDate.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<LocalDate> getEntityClass() {
        return LocalDate.class;
    }

    @Override
    public SQLLocalDateTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLLocalDateTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
