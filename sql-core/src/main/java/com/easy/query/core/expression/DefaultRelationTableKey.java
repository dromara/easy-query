package com.easy.query.core.expression;

import java.util.Objects;

/**
 * create time 2025/3/11 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultRelationTableKey implements RelationTableKey {
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    private final String fullName;

    public DefaultRelationTableKey(Class<?> sourceClass, Class<?> targetClass, String fullName){

        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultRelationTableKey that = (DefaultRelationTableKey) o;
        return Objects.equals(sourceClass, that.sourceClass) && Objects.equals(targetClass, that.targetClass) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass, fullName);
    }

    @Override
    public String toString() {
        return "DefaultRelationTableKey{" +
                "sourceClass=" + sourceClass +
                ", targetClass=" + targetClass +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
