package com.easy.query.api.proxy.entity.save.diff;

import com.easy.query.core.metadata.NavigateMetadata;

import java.util.Objects;

/**
 * create time 2025/11/30 12:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyKey {
    private final Class<?> from;
    private final Class<?> to;
    private final String property;

    public PropertyKey(Class<?> from, Class<?> to, NavigateMetadata navigateMetadata) {
        this.from = from == null ? Object.class : from;
        this.to = to == null ? Object.class : to;
        this.property = navigateMetadata == null ? null : navigateMetadata.getPropertyName();
    }

    public Class<?> getFrom() {
        return from;
    }

    public Class<?> getTo() {
        return to;
    }

    public String getProperty() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PropertyKey that = (PropertyKey) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, property);
    }
}
