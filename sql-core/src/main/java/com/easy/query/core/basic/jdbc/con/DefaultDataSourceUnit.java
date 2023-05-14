package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.exception.EasyQuerySQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * create time 2023/5/14 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultDataSourceUnit implements DataSourceUnit{
    private final DataSource dataSource;
    private final ConnectionStrategyEnum strategy;

    public DefaultDataSourceUnit(DataSource dataSource, ConnectionStrategyEnum strategy){

        this.dataSource = dataSource;
        this.strategy = strategy;
    }
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public ConnectionStrategyEnum getStrategy() {
        return strategy;
    }
}
