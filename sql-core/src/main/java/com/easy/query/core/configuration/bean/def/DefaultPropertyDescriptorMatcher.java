package com.easy.query.core.configuration.bean.def;


import com.easy.query.core.configuration.bean.EasyMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;

import java.beans.PropertyDescriptor;

/**
 * create time 2024/11/1 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultPropertyDescriptorMatcher implements PropertyDescriptorMatcher {

    @Override
    public EasyMatcher create(PropertyDescriptor[] propertyDescriptors) {
        return new DefaultEasyMatcher(propertyDescriptors);
    }
}
