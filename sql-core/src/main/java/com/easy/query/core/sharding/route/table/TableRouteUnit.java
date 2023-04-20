package com.easy.query.core.sharding.route.table;

import java.util.Objects;

/**
 * create time 2023/4/5 13:55
 * 文件说明
 *
 * @author xuejiaming
 */
public final class TableRouteUnit {
    private final String dataSource;
    private final String logicTableName;
    private final String actualTableName;
    private final Class<?> entityClass;

    public TableRouteUnit(String dataSource,String logicTableName, String actualTableName,Class<?> entityClass){

        this.dataSource = dataSource;
        this.logicTableName = logicTableName;
        this.actualTableName = actualTableName;
        this.entityClass = entityClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public String getActualTableName() {
        return actualTableName;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableRouteUnit that = (TableRouteUnit) o;
        return Objects.equals(dataSource, that.dataSource) && Objects.equals(logicTableName, that.logicTableName) && Objects.equals(actualTableName, that.actualTableName) && Objects.equals(entityClass, that.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource, logicTableName, actualTableName, entityClass);
    }
}
