package com.easy.query.core.basic.jdbc.conn.impl;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.conn.DataSourceWrapper;

/**
 * create time 2023/5/14 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultDataSourceWrapper implements DataSourceWrapper {
    private final DataSourceUnit dataSourceUnit;
    private final ConnectionStrategyEnum strategy;

    public DefaultDataSourceWrapper(DataSourceUnit dataSourceUnit, ConnectionStrategyEnum strategy){

        this.dataSourceUnit = dataSourceUnit;
        this.strategy = strategy;
    }
    @Override
    public DataSourceUnit getDataSourceUnit() {
        return dataSourceUnit;
    }

    @Override
    public ConnectionStrategyEnum getStrategy() {
        return strategy;
    }
}
