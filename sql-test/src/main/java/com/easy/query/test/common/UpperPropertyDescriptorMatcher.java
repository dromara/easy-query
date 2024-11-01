package com.easy.query.test.common;

import com.easy.query.core.configuration.bean.EasyMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorResult;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.metadata.PropertyDescriptorFinder;
import com.easy.query.core.util.EasyStringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * create time 2024/11/1 14:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpperPropertyDescriptorMatcher implements PropertyDescriptorMatcher {

    @Override
    public EasyMatcher create(PropertyDescriptor[] propertyDescriptors) {
        return null;
    }
}
