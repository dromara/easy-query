package com.easy.query.core.common;

import java.util.Objects;

/**
 * create time 2024/2/26 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationPath {
    private final Class<?> from;
    private final Class<?> to;
    private final String property;
    private int deep;

    public RelationPath(Class<?> from, Class<?> to, String property, int deep){

        this.from = from;
        this.to = to;
        this.property = property;
        this.deep = deep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationPath that = (RelationPath) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, property);
    }

    public Class<?> getTo() {
        return to;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
