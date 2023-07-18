package com.easy.query.core.metadata;

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
    private PropertyDescriptor[] propertyDescriptors;
    private Map<String, PropertyDescriptor> propertyDescriptorMap;

    public PropertyDescriptorFinder(PropertyDescriptor[] propertyDescriptors) {
        this.mapFind = propertyDescriptors.length > 5;
        if (mapFind) {
            this.propertyDescriptorMap = new HashMap<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                this.propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor);
            }
        } else {
            this.propertyDescriptors = propertyDescriptors;
        }
    }

    public PropertyDescriptor find(String propertyName){
        if(mapFind){
            return this.propertyDescriptorMap.get(propertyName);
        }else{
            return firstOrNull(this.propertyDescriptors, o -> Objects.equals(o.getName(), propertyName));
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
}
