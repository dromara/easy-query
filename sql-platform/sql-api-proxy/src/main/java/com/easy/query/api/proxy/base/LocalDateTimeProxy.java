package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

import java.time.LocalDateTime;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class LocalDateTimeProxy extends AbstractProxyEntity<LocalDateTimeProxy, LocalDateTime> {
    public static LocalDateTimeProxy createTable() {
        return new LocalDateTimeProxy();
    }
    private static final Class<LocalDateTime> entityClass = LocalDateTime.class;

    private LocalDateTimeProxy() {
    }

    @Override
    public Class<LocalDateTime> getEntityClass() {
        return entityClass;
    }

}