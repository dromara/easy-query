package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShortProxy extends AbstractProxyEntity<ShortProxy, Short> {
    public static ShortProxy createTable() {
        return new ShortProxy();
    }
    private static final Class<Short> entityClass = Short.class;

    private ShortProxy() {
    }

    @Override
    public Class<Short> getEntityClass() {
        return entityClass;
    }
}