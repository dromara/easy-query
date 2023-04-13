package com.easy.query.core.sharding;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/4/11 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyDataSource {
    String getDefaultDataSourceName();
    DataSource getDefaultDataSource();

    void addDataSource(String dataSourceName,DataSource dataSource);
    Map<String,DataSource> getAllDataSource();
    DataSource getDataSource(String dataSourceName);
}
