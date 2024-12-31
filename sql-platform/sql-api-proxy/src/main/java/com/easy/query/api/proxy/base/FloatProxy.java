package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class FloatProxy extends AbstractBasicProxyEntity<FloatProxy, Float> {

    private static final Class<Float> entityClass = Float.class;

    public FloatProxy(Float val) {
        set(val);
    }


    public FloatProxy(PropTypeColumn<Float> propTypeColumn) {
        set(propTypeColumn);
    }
    @Override
    public Class<Float> getEntityClass() {
        return entityClass;
    }
}