package com.easy.query.core.proxy.columns.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/12/9 08:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNavigateColumnImpl<TProxy, TProperty> extends SQLColumnImpl<TProxy,TProperty> implements SQLNavigateColumn<TProxy, TProperty> {
    private final Class<TProperty> clazz;

    public SQLNavigateColumnImpl(EntitySQLContext entitySQLContext, TableAvailable table, String property, Class<TProperty> clazz) {
        super(entitySQLContext, table, property);
        this.clazz = clazz;
    }

    @Override
    public Class<TProperty> navigateClass() {
        return clazz;
    }

//    @Override
//    public <TRProxy extends ProxyEntity<TRProxy, T>, T extends ProxyEntityAvailable<T, TRProxy>> Class<T> navigateClass() {
//        return null;
//    }
}
