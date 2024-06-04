package com.easy.query.core.proxy.columns.types.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAnyTypeColumnImpl<TProxy,TProperty> extends SQLColumnImpl<TProxy, TProperty> implements SQLAnyTypeColumn<TProxy,TProperty> {
    public SQLAnyTypeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> propType) {
        super(entitySQLContext, table, property, propType);
    }

    @Override
    public TableAvailable getTableOrNull() {
        return table;
    }

    @Override
    public Class<TProperty> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(propType);
    }

    @Override
    public SQLAnyTypeColumn<TProxy, TProperty> create(TableAvailable table, EntitySQLContext entitySQLContext) {
        return new SQLAnyTypeColumnImpl<>(entitySQLContext,table,property, EasyObjectUtil.typeCastNullable(propType));
    }
}
