package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/9 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNavigateColumnImpl<TProxy, TProperty,TPProxy extends ProxyEntity<TPProxy,TProperty>> extends SQLColumnImpl<TProxy,TProperty> implements SQLNavigateColumn<TProxy, TProperty,TPProxy> {
    public SQLNavigateColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property) {
        super(entitySQLContext, table, property);
    }
}
