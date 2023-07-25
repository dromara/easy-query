package com.easy.query.solon.integration.conn;

import com.easy.query.core.datasource.DefaultDataSourceUnit;
import org.noear.solon.data.tran.TranUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * create time 2023/5/27 23:18
 * 文件说明
 *
 * @author xuejiaming
 */
public class SolonDataSourceUnit extends DefaultDataSourceUnit {
    public SolonDataSourceUnit(String dataSourceName, DataSource dataSource, int mergePoolSize, boolean warningBusy) {
        super(dataSourceName,dataSource,mergePoolSize,warningBusy);
    }

    @Override
    protected Connection getConnection() throws SQLException {
        return TranUtils.getConnection(dataSource);
    }
}
