package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.SQLBooleanColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLBooleanColumnImpl<TProxy, TProperty> extends SQLColumnImpl<TProxy, TProperty> implements SQLBooleanColumn<TProxy, TProperty> {
    public SQLBooleanColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> propType) {
        super(entitySQLContext, table, property, propType);
    }
}
