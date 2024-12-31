package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BooleanProxy extends AbstractBasicProxyEntity<BooleanProxy, Boolean> {
    private static final Class<Boolean> entityClass = Boolean.class;

    public BooleanProxy(Boolean val) {
        set(val);
    }


    public BooleanProxy(PropTypeColumn<Boolean> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Boolean> getEntityClass() {
        return entityClass;
    }
}