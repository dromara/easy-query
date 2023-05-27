package com.easy.query.core.datasource;

import javax.sql.DataSource;

/**
 * create time 2023/5/27 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DataSourceUnitFactory {
    DataSourceUnit createDataSourceUnit(String dataSourceName, DataSource dataSource, int dataSourcePool);
}
