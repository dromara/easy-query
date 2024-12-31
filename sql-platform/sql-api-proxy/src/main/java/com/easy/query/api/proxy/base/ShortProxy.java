package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShortProxy extends AbstractBasicProxyEntity<ShortProxy, Short> {
    private static final Class<Short> entityClass = Short.class;

    public ShortProxy(Short val) {
        set(val);
    }


    public ShortProxy(PropTypeColumn<Short> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<Short> getEntityClass() {
        return entityClass;
    }
}