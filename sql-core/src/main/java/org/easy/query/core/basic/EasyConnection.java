package org.easy.query.core.basic;

import org.easy.query.core.basic.jdbc.Transaction;
import org.easy.query.core.exception.JDQCException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @FileName: EasyConnection.java
 * @Description: 文件说明
 * @Date: 2023/2/21 09:26
 * @Created by xuejiaming
 */
public interface EasyConnection extends AutoCloseable{

    Connection getConnection();
    void setAutoCommit(boolean autoCommit);
    void commit();
    void rollback();
    boolean isClosed();
    void close();
}
