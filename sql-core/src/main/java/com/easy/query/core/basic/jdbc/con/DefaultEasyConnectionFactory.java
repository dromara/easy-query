package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.exception.EasyQuerySQLException;
import com.easy.query.core.sharding.EasyQueryDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * create time 2023/5/12 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyConnectionFactory implements EasyConnectionFactory{
    private final EasyQueryDataSource easyQueryDataSource;

    public DefaultEasyConnectionFactory(EasyQueryDataSource easyQueryDataSource){

        this.easyQueryDataSource = easyQueryDataSource;
    }
    @Override
    public EasyConnection createEasyConnection(String dataSourceName, Integer isolationLevel) {
        try {
            Connection connection = easyQueryDataSource.getDataSourceNotNull(dataSourceName).getConnection();
            return new DefaultEasyConnection(dataSourceName,connection,isolationLevel);
        } catch (SQLException e) {
            throw new EasyQuerySQLException(e);
        }
    }
}
