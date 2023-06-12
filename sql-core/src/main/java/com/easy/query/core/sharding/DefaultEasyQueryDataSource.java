package com.easy.query.core.sharding;

import com.easy.query.core.datasource.DataSourceUnit;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.conn.DataSourceWrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.datasource.DataSourceManager;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/4/12 16:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultEasyQueryDataSource implements EasyQueryDataSource {

    private final EasyQueryOption easyQueryOption;
    private final DataSourceManager dataSourceManager;

    public DefaultEasyQueryDataSource(EasyQueryOption easyQueryOption, DataSourceManager dataSourceManager) {
        this.easyQueryOption = easyQueryOption;
        this.dataSourceManager = dataSourceManager;
    }

    @Override
    public String getDefaultDataSourceName() {
        return dataSourceManager.getDefaultDataSourceName();
    }

    @Override
    public DataSource getDefaultDataSource() {
        return dataSourceManager.getDefaultDataSource();
    }

    @Override
    public boolean addDataSource(String dataSourceName, DataSource dataSource,int dataSourcePoolSize) {
       return dataSourceManager.addDataSource(dataSourceName,dataSource,dataSourcePoolSize);
    }

    @Override
    public Map<String, DataSourceUnit> getAllDataSource() {
        return dataSourceManager.getAllDataSource();
    }

    @Override
    public DataSourceWrapper getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {
        return dataSourceManager.getDataSourceOrNull(dataSourceName,connectionStrategy);
    }
}
