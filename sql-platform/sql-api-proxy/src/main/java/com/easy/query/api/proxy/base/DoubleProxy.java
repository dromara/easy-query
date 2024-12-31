package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DoubleProxy extends AbstractBasicProxyEntity<DoubleProxy, Double> {
    private static final Class<Double> entityClass = Double.class;

    public DoubleProxy(Double val) {
        set(val);
    }


    public DoubleProxy(PropTypeColumn<Double> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Double> getEntityClass() {
        return entityClass;
    }
}