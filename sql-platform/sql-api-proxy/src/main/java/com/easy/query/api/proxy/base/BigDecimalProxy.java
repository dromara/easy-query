package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;

import java.math.BigDecimal;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalProxy extends AbstractBasicProxyEntity<BigDecimalProxy, BigDecimal> {
    private static final Class<BigDecimal> entityClass = BigDecimal.class;

    public BigDecimalProxy(BigDecimal val) {
        set(val);
    }


    public BigDecimalProxy(PropTypeColumn<BigDecimal> propTypeColumn) {
        set(propTypeColumn);
    }

    @Override
    public Class<BigDecimal> getEntityClass() {
        return entityClass;
    }
}