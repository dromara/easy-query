package com.easy.query.core.proxy;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;
import com.easy.query.core.proxy.columns.impl.SQLNavigateColumnImpl;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {

    protected TableAvailable table;
    protected EntitySQLContext entitySQLContext;

    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property) {
        return new SQLColumnImpl<>(entitySQLContext,table, property);
    }
    protected <TProperty> SQLNavigateColumn<TProxy, TProperty> get(String property, Class<TProperty> clazz) {
        return new SQLNavigateColumnImpl<>(entitySQLContext,table, property,clazz);
    }


}
