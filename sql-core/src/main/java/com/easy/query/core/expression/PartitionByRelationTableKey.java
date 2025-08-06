package com.easy.query.core.expression;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Objects;

/**
 * create time 2025/3/11 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionByRelationTableKey implements RelationTableKey {
    private final TableAvailable table;
    private final String property;
    private final int rowNumber;
    private final String sqlKey;

    public PartitionByRelationTableKey(TableAvailable table, String property, int rowNumber, String sqlKey){
        this.table = table;
        this.property = property;

        this.rowNumber = rowNumber;
        this.sqlKey = sqlKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PartitionByRelationTableKey that = (PartitionByRelationTableKey) o;
        return rowNumber == that.rowNumber && Objects.equals(table, that.table) && Objects.equals(property, that.property) && Objects.equals(sqlKey, that.sqlKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, property, rowNumber, sqlKey);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getProperty() {
        return property;
    }
}
