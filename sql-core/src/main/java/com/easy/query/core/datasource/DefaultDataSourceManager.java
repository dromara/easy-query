package com.easy.query.core.datasource;

import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.enums.con.ConnectionStrategyEnum;
import com.easy.query.core.basic.jdbc.con.DataSourceUnit;
import com.easy.query.core.basic.jdbc.con.impl.DefaultDataSourceUnit;
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
    protected final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public DefaultDataSourceManager(EasyQueryOption easyQueryOption, DataSource defaultDataSource) {
        this.defaultDataSourceName = easyQueryOption.getDefaultDataSourceName();
        this.defaultDataSource = defaultDataSource;
        this.dataSourceMap.putIfAbsent(defaultDataSourceName, defaultDataSource);
        initShardingConfig(easyQueryOption);
    }

    private void initShardingConfig(EasyQueryOption easyQueryOption) {

        EasyQueryShardingOption shardingOption = easyQueryOption.getShardingOption();
        if (shardingOption != null) {
            Set<ShardingDataSource> shardingDataSources = shardingOption.getShardingDataSources();
            if (shardingDataSources != null) {
                for (ShardingDataSource shardingDataSource : shardingDataSources) {
                    this.dataSourceMap.putIfAbsent(shardingDataSource.getDataSourceName(), shardingDataSource.getDataSource());
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
    public boolean addDataSource(String dataSourceName, DataSource dataSource) {
        return dataSourceMap.putIfAbsent(dataSourceName, dataSource) == null;
    }

    @Override
    public Map<String, DataSource> getAllDataSource() {
        return dataSourceMap;
    }

    @Override
    public DataSourceUnit getDataSourceOrNull(String dataSourceName, ConnectionStrategyEnum connectionStrategy) {
        if (dataSourceName == null) {
            throw new IllegalArgumentException("dataSourceName");
        }
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        return new DefaultDataSourceUnit(dataSource, connectionStrategy);
    }
}
