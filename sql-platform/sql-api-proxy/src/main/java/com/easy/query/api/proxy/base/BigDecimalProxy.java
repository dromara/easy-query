package com.easy.query.api.proxy.base;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

import java.math.BigDecimal;

/**
 * create time 2023/6/29 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class BigDecimalProxy extends AbstractBasicProxyEntity<BigDecimalProxy, BigDecimal> {
    public static BigDecimalProxy createTable() {
        return new BigDecimalProxy();
    }
    private static final Class<BigDecimal> entityClass = BigDecimal.class;


    private BigDecimalProxy() {
    }

    public BigDecimalProxy(BigDecimal val) {
        set(val);
    }


    public BigDecimalProxy(SQLColumn<?,BigDecimal> sqlColumn) {
        set(sqlColumn);
    }


    public <TResult extends DSLSQLFunctionAvailable & PropTypeColumn<BigDecimal>> BigDecimalProxy(TResult sqlFunctionAvailable) {
        set(sqlFunctionAvailable);
    }

    @Override
    public Class<BigDecimal> getEntityClass() {
        return entityClass;
    }
}