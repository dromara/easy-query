package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.AbstractProxyEntity;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class DoubleProxy extends AbstractProxyEntity<DoubleProxy, Double> {
    public static DoubleProxy createTable() {
        return new DoubleProxy();
    }
    private static final Class<Double> entityClass = Double.class;


    private DoubleProxy() {
    }

    @Override
    public Class<Double> getEntityClass() {
        return entityClass;
    }
}