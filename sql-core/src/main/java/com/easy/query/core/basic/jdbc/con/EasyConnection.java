package com.easy.query.core.basic.jdbc.con;

import java.sql.Connection;

/**
 * @FileName: EasyConnection.java
 * @Description: 文件说明
 * @Date: 2023/2/21 09:26
 * @author xuejiaming
 */
public interface EasyConnection extends AutoCloseable{

    String getDataSourceName();
    Connection getConnection();
    void setAutoCommit(boolean autoCommit);
    void commit();
    void rollback();
    boolean isClosed();
    void close();
}
