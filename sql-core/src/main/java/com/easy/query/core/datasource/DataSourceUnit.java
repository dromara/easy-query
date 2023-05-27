package com.easy.query.core.datasource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/27 22:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceUnit {
    String getDataSourceName();
    DataSource getDataSource();
    List<Connection> getConnections(int count,long timeout, TimeUnit unit) throws SQLException;
}
