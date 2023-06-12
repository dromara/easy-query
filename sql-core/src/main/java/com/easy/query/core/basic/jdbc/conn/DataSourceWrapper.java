package com.easy.query.core.basic.jdbc.conn;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.enums.conn.ConnectionStrategyEnum;

/**
 * create time 2023/5/14 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceWrapper {
    DataSourceUnit getDataSourceUnit();
    ConnectionStrategyEnum getStrategy();
}
