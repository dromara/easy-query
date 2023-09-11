package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class FloatProxy extends AbstractProxyEntity<FloatProxy, Float> {
    public static FloatProxy createTable() {
        return new FloatProxy();
    }

    private static final Class<Float> entityClass = Float.class;


    private FloatProxy() {
    }

    @Override
    public Class<Float> getEntityClass() {
        return entityClass;
    }
}