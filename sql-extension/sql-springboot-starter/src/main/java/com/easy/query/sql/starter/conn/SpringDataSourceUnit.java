package com.easy.query.sql.starter.conn;

import com.easy.query.core.datasource.DefaultDataSourceUnit;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * create time 2023/5/27 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SpringDataSourceUnit extends DefaultDataSourceUnit {
    public SpringDataSourceUnit(String dataSourceName, DataSource dataSource, int mergePoolSize, boolean warningBusy) {
        super(dataSourceName,dataSource,mergePoolSize,warningBusy);
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }
}
