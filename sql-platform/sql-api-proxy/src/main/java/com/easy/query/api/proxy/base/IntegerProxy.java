package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class IntegerProxy extends AbstractProxyEntity<IntegerProxy, Integer> {
    public static IntegerProxy createTable() {
        return new IntegerProxy();
    }

    private static final Class<Integer> entityClass = Integer.class;

    private IntegerProxy() {
    }

    @Override
    public Class<Integer> getEntityClass() {
        return entityClass;
    }
}