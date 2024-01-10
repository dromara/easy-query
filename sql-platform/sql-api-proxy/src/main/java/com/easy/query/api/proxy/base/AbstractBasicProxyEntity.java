package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractBaseProxyEntity;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.impl.SQLColumnSetPropColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;

/**
 * create time 2023/12/28 07:58
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBasicProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractBaseProxyEntity<TProxy,TEntity> {
    protected void set(TEntity val) {
        getEntitySQLContext().accept(new SQLColumnSetValueImpl(null, null, val));
    }
    protected void set(PropTypeColumn<TEntity> val) {
        getEntitySQLContext().accept(new SQLColumnSetPropColumnImpl(null, null, val));
    }
}
