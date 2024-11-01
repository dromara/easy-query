package com.easy.query.core.configuration.bean;

import java.beans.PropertyDescriptor;

/**
 * create time 2024/11/1 16:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyDescriptorResult {
    private final String propertyName;
    private final PropertyDescriptor propertyDescriptor;

    public PropertyDescriptorResult(String propertyName, PropertyDescriptor propertyDescriptor){
        this.propertyName = propertyName;
        this.propertyDescriptor = propertyDescriptor;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }
}
