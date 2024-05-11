package com.easy.query.test.entity.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2024/5/9 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLStringTypeColumnImpl<TProxy> extends SQLColumnImpl<TProxy, String> implements MySQLStringTypeColumn<TProxy> {
    public MySQLStringTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
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
    public MySQLStringTypeColumn<TProxy> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new MySQLStringTypeColumnImpl<>(entitySQLContext,table,property);
    }

    @Override
    public void asx() {

    }
}
