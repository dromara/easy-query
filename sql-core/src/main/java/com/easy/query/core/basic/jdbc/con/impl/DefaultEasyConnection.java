package com.easy.query.core.basic.jdbc.con.impl;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.EasyConnection;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xuejiaming
 * @FileName: DefaultEasyConnection.java
 * @Description: 文件说明
 * @Date: 2023/2/21 09:26
 */
public class DefaultEasyConnection implements EasyConnection {
    private static final Log log= LogFactory.getLog(DefaultEasyConnection.class);
    private final String dataSourceName;
    private final ConnectionStrategyEnum connectionStrategy;
    private final Connection connection;
    private final Integer isolationLevel;
    private boolean closed = false;

    public DefaultEasyConnection(String dataSourceName,ConnectionStrategyEnum connectionStrategy, Connection connection, Integer isolationLevel) {
        this.dataSourceName = dataSourceName;
        this.connectionStrategy = connectionStrategy;

        this.connection = connection;
        this.isolationLevel = isolationLevel;
        setIsolationLevel();
    }

    @Override
    public ConnectionStrategyEnum getConnectionStrategy() {
        return connectionStrategy;
    }

    @Override
    public String getDataSourceName() {
        return dataSourceName;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    private void setIsolationLevel() {
        if (!closed) {
            if(isolationLevel!=null){
                try {
                    connection.setTransactionIsolation(isolationLevel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();
    }

    @Override
    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void close() {
        close(true);
    }

    public void close(boolean closeConnection) {
        if (closed) {
            return;
        }
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    if (closeConnection) {
                        connection.close();
                    }
                }
            } catch (SQLException ex) {
                log.error("close connection error.",ex);

            }
        }
        closed = true;
    }
}
