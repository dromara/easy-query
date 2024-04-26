package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLLocalTimeTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.time.LocalTime;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLLocalTimeTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, LocalTime> implements SQLLocalTimeTypeColumn<TProxy> {
    public SQLLocalTimeTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, LocalTime.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<LocalTime> getEntityClass() {
        return LocalTime.class;
    }

    @Override
    public SQLLocalTimeTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLLocalTimeTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
