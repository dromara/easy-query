package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShortProxy extends AbstractBasicProxyEntity<ShortProxy, Short> {
    public static ShortProxy createTable() {
        return new ShortProxy();
    }
    private static final Class<Short> entityClass = Short.class;

    private ShortProxy() {
    }
    public ShortProxy(Short val) {
        set(val);
    }


    public ShortProxy(SQLColumn<?,Short> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Short>> ShortProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<Short> getEntityClass() {
        return entityClass;
    }
}