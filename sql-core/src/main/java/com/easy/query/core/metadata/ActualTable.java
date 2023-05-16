package com.easy.query.core.metadata;

import java.util.Objects;

/**
 * create time 2023/5/16 08:32
 * 文件说明
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

    public String getDataSourceName() {
        return dataSourceName;
    }

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
}
