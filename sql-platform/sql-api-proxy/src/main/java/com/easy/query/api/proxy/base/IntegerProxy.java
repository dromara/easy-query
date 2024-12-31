package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerProxy extends AbstractBasicProxyEntity<IntegerProxy, Integer> {

    private static final Class<Integer> entityClass = Integer.class;

    public IntegerProxy(Integer val) {
        set(val);
    }


    public IntegerProxy(PropTypeColumn<Integer> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Integer> getEntityClass() {
        return entityClass;
    }
}