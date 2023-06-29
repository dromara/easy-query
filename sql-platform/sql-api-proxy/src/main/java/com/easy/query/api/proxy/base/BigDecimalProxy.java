package com.easy.query.api.proxy.base;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.ProxyEntity;

import java.math.BigDecimal;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalProxy implements ProxyEntity<BigDecimalProxy, BigDecimal> {

    public static final BigDecimalProxy DEFAULT = new BigDecimalProxy();
    private static final Class<BigDecimal> entityClass = BigDecimal.class;

    private final TableAvailable table;

    private BigDecimalProxy() {
        this.table = null;
    }

    public BigDecimalProxy(TableAvailable table) {
        this.table = table;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public Class<BigDecimal> getEntityClass() {
        return entityClass;
    }

    @Override
    public BigDecimalProxy create(TableAvailable table) {
        return new BigDecimalProxy(table);
    }
}