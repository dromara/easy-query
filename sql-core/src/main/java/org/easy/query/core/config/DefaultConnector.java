package org.easy.query.core.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @FileName: DefaultConnector.java
 * @Description: 文件说明
 * @Date: 2023/2/16 21:58
 * @Created by xuejiaming
 */
public class DefaultConnector implements EasyConnector{
    private final DefaultConfig defaultConfig;

    public DefaultConnector(DefaultConfig defaultConfig){

        this.defaultConfig = defaultConfig;
    }

    @Override
    public Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName(defaultConfig.getDriver());
            conn = DriverManager.getConnection(defaultConfig.getUrl(), defaultConfig.getUsername(),
                    defaultConfig.getPassword());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("驱动未发现:"+defaultConfig.getDriver(),e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    @Override
    public void closeConnection(Connection connection) {

    }
}
