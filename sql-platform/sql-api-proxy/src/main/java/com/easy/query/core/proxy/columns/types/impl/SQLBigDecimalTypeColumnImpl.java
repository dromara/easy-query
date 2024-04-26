package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

import java.math.BigDecimal;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLBigDecimalTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, BigDecimal> implements SQLBigDecimalTypeColumn<TProxy> {
    public SQLBigDecimalTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property, BigDecimal.class);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<BigDecimal> getEntityClass() {
        return BigDecimal.class;
    }

    @Override
    public SQLBigDecimalTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLBigDecimalTypeColumnImpl<>(entitySQLContext,table,property);
    }
}
