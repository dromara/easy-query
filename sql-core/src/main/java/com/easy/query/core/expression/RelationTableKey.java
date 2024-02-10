package com.easy.query.core.expression;

import java.util.Objects;

/**
 * create time 2024/2/10 17:57
 * 文件说明
 *
 * @author xuejiaming
 */
public final class RelationTableKey {
    private final Class<?> sourceClass;
    private final Class<?> targetClass;

    public RelationTableKey(Class<?> sourceClass, Class<?> targetClass){

        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationTableKey that = (RelationTableKey) o;
        return Objects.equals(sourceClass, that.sourceClass) && Objects.equals(targetClass, that.targetClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass);
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }
}
