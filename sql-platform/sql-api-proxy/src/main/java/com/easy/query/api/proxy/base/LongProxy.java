package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LongProxy extends AbstractProxyEntity<LongProxy, Long> {
    public static LongProxy createTable() {
        return new LongProxy();
    }
    private static final Class<Long> entityClass = Long.class;

    private LongProxy() {
    }

    @Override
    public Class<Long> getEntityClass() {
        return entityClass;
    }
}