package com.easy.query.core.common;

import java.util.Objects;

/**
 * create time 2024/2/26 21:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class IncludePath {
    private final Class<?> from;
    private final Class<?> to;
    private final String property;

    public IncludePath(Class<?> from, Class<?> to,String property){

        this.from = from;
        this.to = to;
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncludePath that = (IncludePath) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, property);
    }
}
