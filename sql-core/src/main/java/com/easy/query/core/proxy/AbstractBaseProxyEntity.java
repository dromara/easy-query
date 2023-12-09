package com.easy.query.core.proxy;

import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.columns.SQLNavigateOneColumn;
import com.easy.query.core.proxy.columns.impl.SQLNavigateOneColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnImpl;

/**
 * create time 2023/6/25 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> implements ProxyEntity<TProxy, TEntity>, EntitySQLContextAvailable {


    protected <TProperty> SQLColumn<TProxy, TProperty> get(String property) {
        return new SQLColumnImpl<>(getEntitySQLContext(),getTable(), property);
    }
    protected <TProperty> SQLNavigateOneColumn<TProxy, TProperty> getNavigateOne(String property) {
        return new SQLNavigateOneColumnImpl<>(getEntitySQLContext(),getTable(), property);
    }


}
