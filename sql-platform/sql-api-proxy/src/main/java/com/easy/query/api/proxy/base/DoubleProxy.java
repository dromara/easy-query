package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DoubleProxy extends AbstractBasicProxyEntity<DoubleProxy, Double> {
    public static DoubleProxy createTable() {
        return new DoubleProxy();
    }
    private static final Class<Double> entityClass = Double.class;


    private DoubleProxy() {
    }
    public DoubleProxy(Double val) {
        set(val);
    }


    public DoubleProxy(SQLColumn<?,Double> sqlColumn) {
        set(sqlColumn);
    }


    public DoubleProxy(DSLSQLFunctionAvailable sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<Double> getEntityClass() {
        return entityClass;
    }
}