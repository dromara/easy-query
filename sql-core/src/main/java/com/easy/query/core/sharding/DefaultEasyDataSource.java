package com.easy.query.core.sharding;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/4/12 16:03
 * 文件说明
 *
 * @author xuejiaming
 */
public final class DefaultEasyDataSource implements EasyDataSource {
    private final String defaultDataSourceName;
    private final DataSource defaultDataSource;
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public DefaultEasyDataSource(String defaultDataSourceName, DataSource defaultDataSource) {

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
    public void addDataSource(String dataSourceName, DataSource dataSource) {
        if (dataSourceMap.containsKey(dataSourceName)) {
            throw new EasyQueryInvalidOperationException("repeat data source:" + dataSourceName);
        }
        dataSourceMap.put(dataSourceName, dataSource);
    }

    @Override
    public Map<String, DataSource> getAllDataSource() {
        return dataSourceMap;
    }

    @Override
    public DataSource getDataSource(String dataSourceName) {
        if(dataSourceName==null){
            throw new IllegalArgumentException("dataSourceName");
        }
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        if(dataSource==null){
            throw new EasyQueryInvalidOperationException("not found data source :"+dataSourceName);
        }
        return dataSource;
    }
}
