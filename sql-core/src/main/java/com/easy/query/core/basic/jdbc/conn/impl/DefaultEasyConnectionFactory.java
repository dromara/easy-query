package com.easy.query.core.basic.jdbc.conn.impl;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.conn.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.conn.DataSourceWrapper;
import com.easy.query.core.basic.jdbc.conn.EasyConnection;
import com.easy.query.core.basic.jdbc.conn.EasyConnectionFactory;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.sharding.EasyQueryDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/12 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyConnectionFactory implements EasyConnectionFactory {
    private final EasyQueryDataSource easyQueryDataSource;
    private final EasyQueryOption easyQueryOption;

    public DefaultEasyConnectionFactory(EasyQueryDataSource easyQueryDataSource, EasyQueryOption easyQueryOption){

        this.easyQueryDataSource = easyQueryDataSource;
        this.easyQueryOption = easyQueryOption;
    }
    @Override
    public List<EasyConnection> createEasyConnections(int count, String dataSourceName, Integer isolationLevel, ConnectionStrategyEnum connectionStrategy) {
        try {
            DataSourceWrapper dataSourceWrapper = easyQueryDataSource.getDataSourceNotNull(dataSourceName, connectionStrategy);
            DataSourceUnit dataSourceUnit = dataSourceWrapper.getDataSourceUnit();
            long multiConnWaitTimeoutMillis = easyQueryOption.getMultiConnWaitTimeoutMillis();
            List<Connection> connections = dataSourceUnit.getConnections(count, multiConnWaitTimeoutMillis, TimeUnit.MILLISECONDS);
            List<EasyConnection> easyConnections = new ArrayList<>(count);
            for (Connection connection : connections) {
                EasyConnection easyConnection = createConnection(connection,dataSourceName,dataSourceWrapper.getStrategy(),isolationLevel);
                easyConnections.add(easyConnection);
            }
            return easyConnections;
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    @Override
    public EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel, ConnectionStrategyEnum connectionStrategy) {
        try {
            DataSourceWrapper dataSourceWrapper = easyQueryDataSource.getDataSourceNotNull(dataSourceName, connectionStrategy);
            DataSourceUnit dataSourceUnit = dataSourceWrapper.getDataSourceUnit();
            long multiConnWaitTimeoutMillis = easyQueryOption.getMultiConnWaitTimeoutMillis();

            Connection connection = dataSourceUnit.getConnection(multiConnWaitTimeoutMillis, TimeUnit.MILLISECONDS);
            return createConnection(connection, dataSourceName, dataSourceWrapper.getStrategy(), isolationLevel);

        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    private EasyConnection createConnection(Connection connection,String dataSourceName,ConnectionStrategyEnum connectionStrategy,Integer isolationLevel){
        return new DefaultEasyConnection(dataSourceName, connectionStrategy, connection, isolationLevel);
    }
}
