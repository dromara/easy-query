package com.easy.query.core.metadata;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * create time 2023/7/18 14:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyDescriptorFinder {
    private final boolean mapFind;
    private final PropertyDescriptor[] propertyDescriptors;
    private Map<String, PropertyDescriptor> propertyDescriptorMap;


    public PropertyDescriptorFinder(PropertyDescriptor[] propertyDescriptors) {
        this.mapFind = propertyDescriptors.length > 5;
        if (mapFind) {
            this.propertyDescriptorMap = new HashMap<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                this.propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);
            }
        }
        this.propertyDescriptors = propertyDescriptors;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors;
    }

    public PropertyDescriptor find(String propertyName) {
        if (mapFind) {
            return this.propertyDescriptorMap.get(propertyName);
        } else {
            return firstOrNull(this.propertyDescriptors, o -> Objects.equals(o.getName(), propertyName));
        }
    }

    public PropertyDescriptor findIgnoreCase(String propertyName) {
        if (propertyName == null) {
            throw new EasyQueryInvalidOperationException("propertyName is null");
        }
        if (mapFind) {
            return firstOrNull(this.propertyDescriptorMap, o -> propertyName.equalsIgnoreCase(o.getName()));
        } else {
            return firstOrNull(this.propertyDescriptors, o -> propertyName.equalsIgnoreCase(o.getName()));
        }
    }

    private PropertyDescriptor firstOrNull(PropertyDescriptor[] ps, Predicate<PropertyDescriptor> predicate) {
        for (PropertyDescriptor p : ps) {
            if (predicate.test(p)) {
                return p;
            }
        }
        return null;
    }

    private PropertyDescriptor firstOrNull(Map<String, PropertyDescriptor> propertyDescriptorMap, Predicate<PropertyDescriptor> predicate) {
        for (PropertyDescriptor p : propertyDescriptorMap.values()) {
            if (predicate.test(p)) {
                return p;
            }
        }
        return null;
    }
}
