package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.AbstractProxyEntity;

import java.math.BigDecimal;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalProxy extends AbstractProxyEntity<BigDecimalProxy, BigDecimal> {
    public static BigDecimalProxy createTable() {
        return new BigDecimalProxy();
    }
    private static final Class<BigDecimal> entityClass = BigDecimal.class;


    private BigDecimalProxy() {
    }


    @Override
    public Class<BigDecimal> getEntityClass() {
        return entityClass;
    }
}