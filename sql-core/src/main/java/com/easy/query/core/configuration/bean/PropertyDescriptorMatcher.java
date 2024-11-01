package com.easy.query.core.configuration.bean;


import java.beans.PropertyDescriptor;

/**
 * create time 2024/11/1 14:20
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertyDescriptorMatcher {
    EasyMatcher create(PropertyDescriptor[] propertyDescriptors);
}
