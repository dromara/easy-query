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
    private final String fullName;

    public RelationTableKey(Class<?> sourceClass, Class<?> targetClass,String fullName){

        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RelationTableKey that = (RelationTableKey) o;
        return Objects.equals(sourceClass, that.sourceClass) && Objects.equals(targetClass, that.targetClass) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceClass, targetClass, fullName);
    }
}
