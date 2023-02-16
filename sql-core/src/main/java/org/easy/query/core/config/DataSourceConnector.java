package org.easy.query.core.config;

import org.easy.query.core.exception.JDQCException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @FileName: DataSourceConnector.java
 * @Description: 文件说明
 * @Date: 2023/2/16 22:07
 * @Created by xuejiaming
 */
public class DataSourceConnector implements EasyConnector{
    private final EasyDataSourceFactory easyDataSourceFactory;

    public DataSourceConnector(EasyDataSourceFactory easyDataSourceFactory){

        this.easyDataSourceFactory = easyDataSourceFactory;
    }
    @Override
    public Connection getConnection() {
        try {
            return easyDataSourceFactory.createDataSource().getConnection();
        } catch (SQLException e) {
            throw new JDQCException(e);
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new JDQCException(e);
        }
    }
}
