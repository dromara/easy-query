//package org.easy.query.core.config;
//
//import org.easy.query.core.basic.DefaultEasyConnection;
//import org.easy.query.core.basic.EasyConnection;
//import org.easy.query.core.exception.JDQCException;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * @FileName: DataSourceConnector.java
// * @Description: 文件说明
// * @Date: 2023/2/16 22:07
// * @Created by xuejiaming
// */
//public class DataSourceConnector implements EasyConnector {
//
//    private final DataSource dataSource;
//
//    public DataSourceConnector(DataSource dataSource) {
//
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    public EasyConnection getConnection() {
//        return new DefaultEasyConnection(doGetConnection());
//    }
//
//    @Override
//    public void closeConnection(EasyConnection easyConnection) {
//
//    }
//
//    protected Connection doGetConnection() {
//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            throw new JDQCException(e);
//        }
//    }
//}
