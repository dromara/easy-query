package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class StringProxy extends AbstractProxyEntity<StringProxy, String> {
    public static StringProxy createTable() {
        return new StringProxy();
    }
    private static final Class<String> entityClass = String.class;

    private StringProxy() {
    }

    @Override
    public Class<String> getEntityClass() {
        return entityClass;
    }

}