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
    private final String tail;
    private final Class<?> entityClass;

    public TableRouteUnit(String dataSource, String tail, Class<?> entityClass){

        this.dataSource = dataSource;
        this.tail = tail;
        this.entityClass = entityClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getTail() {
        return tail;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableRouteUnit that = (TableRouteUnit) o;
        return dataSource.equals(that.dataSource) && tail.equals(that.tail) && entityClass.equals(that.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource, tail, entityClass);
    }
}
