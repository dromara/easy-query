package com.easy.query.core.basic.jdbc.con;

import com.easy.query.core.enums.con.ConnectionStrategyEnum;

import javax.sql.DataSource;

/**
 * create time 2023/5/14 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceUnit {
    DataSource getDataSource();
    ConnectionStrategyEnum getStrategy();
}
