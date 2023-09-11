package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

import java.sql.Timestamp;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimestampProxy extends AbstractProxyEntity<TimestampProxy, Timestamp> {
    public static TimestampProxy createTable() {
        return new TimestampProxy();
    }
    private static final Class<Timestamp> entityClass = Timestamp.class;

    private TimestampProxy() {
    }

    @Override
    public Class<Timestamp> getEntityClass() {
        return entityClass;
    }

}