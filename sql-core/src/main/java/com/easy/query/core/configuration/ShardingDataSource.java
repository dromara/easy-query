package com.easy.query.core.configuration;

import com.easy.query.core.util.EasyStringUtil;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * create time 2023/5/25 13:16
 * 文件说明
 *
 * @author xuejiaming
 */
public final class ShardingDataSource {
    private final String dataSourceName;
    private final DataSource dataSource;
    private final int dataSourceMergePoolSize;

    public ShardingDataSource(String dataSourceName, DataSource dataSource, int dataSourceMergePoolSize) {
        if (EasyStringUtil.isBlank(dataSourceName)) {
            throw new IllegalArgumentException("dataSourceName is empty");
        }
        if (dataSourceMergePoolSize <= 0) {
            throw new IllegalArgumentException("dataSourceMergePoolSize <= 0");
        }
        this.dataSourceName = dataSourceName;
        this.dataSource = dataSource;
        this.dataSourceMergePoolSize = dataSourceMergePoolSize;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public int getDataSourceMergePoolSize() {
        return dataSourceMergePoolSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShardingDataSource that = (ShardingDataSource) o;
        return Objects.equals(dataSourceName, that.dataSourceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSourceName);
    }
}
