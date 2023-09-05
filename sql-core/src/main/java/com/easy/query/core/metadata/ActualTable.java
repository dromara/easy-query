package com.easy.query.core.metadata;

import java.util.Objects;

/**
 * create time 2023/5/16 08:32
 * 分片后实际的表和所在的数据源关系
 *
 * @author xuejiaming
 */
public class ActualTable {
    private final String dataSourceName;
    private final String actualTableName;

    public ActualTable(String dataSourceName, String actualTableName){

        this.dataSourceName = dataSourceName;
        this.actualTableName = actualTableName;
    }

    /**
     * 所属数据源名称
     * eg. ds0 ds1 ds2 ...
     * @return 数据源名称
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * 实际的表名
     * eg. order_00 order_01 ...
     * @return 表名
     */
    public String getActualTableName() {
        return actualTableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActualTable dataNode = (ActualTable) o;
        return Objects.equals(dataSourceName, dataNode.dataSourceName) && Objects.equals(actualTableName, dataNode.actualTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSourceName, actualTableName);
    }

    @Override
    public String toString() {
        return "{" +
                "dataSourceName='" + dataSourceName + '\'' +
                ", actualTableName='" + actualTableName + '\'' +
                '}';
    }
}
