package org.easy.query.core.basic.jdbc.con;

import org.easy.query.core.exception.EasyQueryException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @FileName: DefaultEasyConnection.java
 * @Description: 文件说明
 * @Date: 2023/2/21 09:26
 * @Created by xuejiaming
 */
public class DefaultEasyConnection implements EasyConnection {
    private final Connection connection;
    private final Integer isolationLevel;
    private boolean closed = false;
    private boolean autoCommit;

    public DefaultEasyConnection(Connection connection, Integer isolationLevel) {

        this.connection = connection;
        this.isolationLevel = isolationLevel;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            this.autoCommit = autoCommit;
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
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
        if (closed) {
            return;
        }
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    if (!autoCommit) {
                        connection.setAutoCommit(true);
                    }
                    if (this.isolationLevel != null) {
                        connection.setTransactionIsolation(this.isolationLevel);
                    }
                    connection.close();
                }
            } catch (SQLException ex) {
                System.err.println("close connection error: "+ ex.getMessage());

            }
        }
        closed = true;
    }
}
