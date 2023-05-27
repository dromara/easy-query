package com.easy.query.core.datasource.replica;

import com.easy.query.core.datasource.DataSourceManager;
import com.easy.query.core.datasource.DataSourceUnit;

import javax.sql.DataSource;

/**
 * create time 2023/5/12 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ReplicaDataSourceManager extends DataSourceManager {

    boolean addDataSource(String dataSourceName,String replicaAlias,DataSource dataSource,int dataSourcePoolSize);
    DataSourceUnit getDataSourceOrNull(String dataSourceName, String replicaAlias);
}
