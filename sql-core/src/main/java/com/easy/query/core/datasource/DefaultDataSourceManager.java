package com.easy.query.core.datasource;

import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.DataSourceWrapper;
import com.easy.query.core.basic.jdbc.con.impl.DefaultDataSourceWrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.EasyQueryShardingOption;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/5/12 13:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultDataSourceManager implements DataSourceManager {
    protected final String defaultDataSourceName;
    protected final DataSource defaultDataSource;
    protected final DataSourceUnitFactory dataSourceUnitFactory;
    protected final Map<String, DataSourceUnit> dataSourceMap = new ConcurrentHashMap<>();

    public DefaultDataSourceManager(EasyQueryOption easyQueryOption, DataSource defaultDataSource,DataSourceUnitFactory dataSourceUnitFactory) {
        this.defaultDataSourceName = easyQueryOption.getDefaultDataSourceName();
        this.defaultDataSource = defaultDataSource;
        this.dataSourceUnitFactory = dataSourceUnitFactory;
        this.dataSourceMap.putIfAbsent(defaultDataSourceName, dataSourceUnitFactory.createDataSourceUnit(defaultDataSourceName,defaultDataSource,easyQueryOption.getDefaultDataSourcePoolSize()));
        initShardingConfig(easyQueryOption);
    }

    private void initShardingConfig(EasyQueryOption easyQueryOption) {

        EasyQueryShardingOption shardingOption = easyQueryOption.getShardingOption();
        if (shardingOption != null) {
            Set<ShardingDataSource> shardingDataSources = shardingOption.getShardingDataSources();
            if (shardingDataSources != null) {
                for (ShardingDataSource shardingDataSource : shardingDataSources) {
                    this.dataSourceMap.putIfAbsent(shardingDataSource.getDataSourceName(), dataSourceUnitFactory.createDataSourceUnit(shardingDataSource.getDataSourceName(),shardingDataSource.getDataSource(),shardingDataSource.getDataSourcePoolSize()));
                }
            }
        }
    }

    @Override
    public String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    @Override
    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

    @Override
    public boolean addDataSource(String dataSourceName, DataSource dataSource,int dataSourcePoolSize) {
        return dataSourceMap.putIfAbsent(dataSourceName, dataSourceUnitFactory.createDataSourceUnit(dataSourceName,dataSource,dataSourcePoolSize)) == null;
    }

    @Override
    public Map<String, DataSourceUnit> getAllDataSource() {
        return dataSourceMap;
    }

    @Override
    public DataSourceWrapper getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {
        if (dataSourceName == null) {
            throw new IllegalArgumentException("dataSourceName");
        }
        DataSourceUnit dataSource = dataSourceMap.get(dataSourceName);
        return new DefaultDataSourceWrapper(dataSource, connectionStrategy);
    }
}
