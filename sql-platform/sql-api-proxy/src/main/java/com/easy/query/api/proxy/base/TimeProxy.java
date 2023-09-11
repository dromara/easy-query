package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;

import java.sql.Time;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimeProxy extends AbstractProxyEntity<TimeProxy, Time> {
    public static TimeProxy createTable() {
        return new TimeProxy();
    }
    private static final Class<Time> entityClass = Time.class;

    private TimeProxy() {
    }

    @Override
    public Class<Time> getEntityClass() {
        return entityClass;
    }

}