package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.impl.SQLColumnSetColumnImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetSQLFunctionValueImpl;
import com.easy.query.core.proxy.impl.SQLColumnSetValueImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/28 07:58
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBasicProxyEntity<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> extends AbstractProxyEntity<TProxy,TEntity> {
    protected void set(SQLColumn<?,String> column) {
        getEntitySQLContext().accept(new SQLColumnSetColumnImpl(null, null, column));
    }
    protected void set(Object val) {
        getEntitySQLContext().accept(new SQLColumnSetValueImpl(null, null, val));
    }
    protected void set(DSLSQLFunctionAvailable val) {
        getEntitySQLContext().accept(new SQLColumnSetSQLFunctionValueImpl(null, null, val));
    }
}
