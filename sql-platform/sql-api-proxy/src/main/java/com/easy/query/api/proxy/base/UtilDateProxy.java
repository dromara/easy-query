package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

import java.util.Date;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class UtilDateProxy extends AbstractProxyEntity<UtilDateProxy, Date> {
    public static UtilDateProxy createTable() {
        return new UtilDateProxy();
    }

    private static final Class<java.util.Date> entityClass = java.util.Date.class;


    private UtilDateProxy() {
    }

    @Override
    public Class<java.util.Date> getEntityClass() {
        return entityClass;
    }
}