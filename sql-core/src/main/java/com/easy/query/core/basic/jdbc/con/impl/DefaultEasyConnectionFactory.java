package com.easy.query.core.basic.jdbc.con.impl;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.DataSourceUnit;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.basic.jdbc.con.EasyConnectionFactory;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.sharding.EasyQueryDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * create time 2023/5/12 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyConnectionFactory implements EasyConnectionFactory {
    private final EasyQueryDataSource easyQueryDataSource;

    public DefaultEasyConnectionFactory(EasyQueryDataSource easyQueryDataSource){

        this.easyQueryDataSource = easyQueryDataSource;
    }
    @Override
    public EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel, ConnectionStrategyEnum connectionStrategy) {
        try {
            DataSourceUnit dataSourceUnit = easyQueryDataSource.getDataSourceNotNull(dataSourceName, connectionStrategy);
            Connection connection = dataSourceUnit.getDataSource().getConnection();
            return new DefaultEasyConnection(dataSourceName,dataSourceUnit.getStrategy(),connection,isolationLevel);
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }
}
