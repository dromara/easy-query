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
public class FloatProxy extends AbstractBasicProxyEntity<FloatProxy, Float> {
    public static FloatProxy createTable() {
        return new FloatProxy();
    }

    private static final Class<Float> entityClass = Float.class;


    private FloatProxy() {
    }
    public FloatProxy(Float val) {
        set(val);
    }


    public FloatProxy(SQLColumn<?,Float> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Float>> FloatProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }
    @Override
    public Class<Float> getEntityClass() {
        return entityClass;
    }
}