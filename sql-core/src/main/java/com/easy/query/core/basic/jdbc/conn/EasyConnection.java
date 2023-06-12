package com.easy.query.core.basic.jdbc.conn;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @FileName: EasyConnection.java
 * @Description: 文件说明
 * @Date: 2023/2/21 09:26
 * @author xuejiaming
 */
public interface EasyConnection extends AutoCloseable{

    ConnectionStrategyEnum getConnectionStrategy();
    String getDataSourceName();
    Connection getConnection();
    void setAutoCommit(boolean autoCommit);
    void commit() throws SQLException;
    void rollback() throws SQLException;
    boolean isClosed();
    void close();
}
