package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BooleanProxy extends AbstractProxyEntity<BooleanProxy, Boolean> {
    public static BooleanProxy createTable() {
        return new BooleanProxy();
    }
    private static final Class<Boolean> entityClass = Boolean.class;


    private BooleanProxy() {
    }


    @Override
    public Class<Boolean> getEntityClass() {
        return entityClass;
    }
}