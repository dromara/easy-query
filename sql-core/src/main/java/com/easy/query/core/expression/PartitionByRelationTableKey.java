package com.easy.query.core.expression;

import java.util.Objects;

/**
 * create time 2025/3/11 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionByRelationTableKey implements RelationTableKey {
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    private final String fullName;
    private final int rowNumber;
    private final String sqlKey;

    public PartitionByRelationTableKey(Class<?> sourceClass, Class<?> targetClass, String fullName,int rowNumber,String sqlKey){

        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.fullName = fullName;
        this.rowNumber = rowNumber;
        this.sqlKey = sqlKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PartitionByRelationTableKey that = (PartitionByRelationTableKey) o;
        return rowNumber == that.rowNumber && Objects.equals(sourceClass, that.sourceClass) && Objects.equals(targetClass, that.targetClass) && Objects.equals(fullName, that.fullName) && Objects.equals(sqlKey, that.sqlKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass, fullName, rowNumber, sqlKey);
    }

    @Override
    public String toString() {
        return "PartitionByRelationTableKey{" +
                "sourceClass=" + sourceClass +
                ", targetClass=" + targetClass +
                ", fullName='" + fullName + '\'' +
                ", rowNumber=" + rowNumber +
                ", sqlKey='" + sqlKey + '\'' +
                '}';
    }
}
