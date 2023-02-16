package org.easy.query.core.config;

import java.sql.Connection;

/**
 * @FileName: EasyConnector.java
 * @Description: 文件说明
 * @Date: 2023/2/16 21:56
 * @Created by xuejiaming
 */
public interface EasyConnector {
    Connection getConnection();
    void closeConnection(Connection connection);
}
