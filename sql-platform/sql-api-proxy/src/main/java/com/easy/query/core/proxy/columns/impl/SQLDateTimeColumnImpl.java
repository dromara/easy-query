package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.SQLDateTimeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/24 00:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLDateTimeColumnImpl<TProxy, TProperty> extends SQLColumnImpl<TProxy, TProperty> implements SQLDateTimeColumn<TProxy, TProperty> {
    public SQLDateTimeColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> propType) {
        super(entitySQLContext, table, property, propType);
    }
}
