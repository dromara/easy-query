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
public class IntegerProxy extends AbstractBasicProxyEntity<IntegerProxy, Integer> {
    public static IntegerProxy createTable() {
        return new IntegerProxy();
    }

    private static final Class<Integer> entityClass = Integer.class;

    private IntegerProxy() {
    }
    public IntegerProxy(Integer val) {
        set(val);
    }


    public IntegerProxy(SQLColumn<?,Integer> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<Integer>> IntegerProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<Integer> getEntityClass() {
        return entityClass;
    }
}