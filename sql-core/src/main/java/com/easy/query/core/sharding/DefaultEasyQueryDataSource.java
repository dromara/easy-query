package com.easy.query.core.sharding;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/12 16:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultEasyQueryDataSource implements EasyQueryDataSource {
    private final String defaultDataSourceName;
    private final DataSource defaultDataSource;
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public DefaultEasyQueryDataSource(String defaultDataSourceName, DataSource defaultDataSource) {

        this.defaultDataSourceName = defaultDataSourceName;
        this.defaultDataSource = defaultDataSource;
        dataSourceMap.putIfAbsent(defaultDataSourceName, defaultDataSource);
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
        DataSource existDataSource = dataSourceMap.computeIfAbsent(dataSourceName, k -> dataSource);
        return Objects.equals(existDataSource,dataSource);
    }

    @Override
    public Map<String, DataSource> getAllDataSource() {
        return dataSourceMap;
    }

    @Override
    public DataSource getDataSourceOrNull(String dataSourceName) {
        if (dataSourceName == null) {
            throw new IllegalArgumentException("dataSourceName");
        }
        return dataSourceMap.get(dataSourceName);
    }
}
