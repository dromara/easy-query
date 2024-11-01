package com.easy.query.core.configuration.bean.entity;

import com.easy.query.core.configuration.bean.EasyMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorResult;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * create time 2024/11/1 16:57
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityEasyMatcher implements EasyMatcher {
    private final Map<String,List<PropertyDescriptor>> propertyDescriptorsMap;
    public EntityEasyMatcher(PropertyDescriptor[] propertyDescriptors){
        propertyDescriptorsMap = new HashMap<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            List<PropertyDescriptor> list = propertyDescriptorsMap.computeIfAbsent(propertyDescriptor.getName().toLowerCase(Locale.ENGLISH), o -> new ArrayList<>());
            list.add(propertyDescriptor);
        }
    }
    @Override
    public PropertyDescriptorResult match(Class<?> entityClass, Field field) {
        List<PropertyDescriptor> list = propertyDescriptorsMap.get(field.getName().toLowerCase(Locale.ENGLISH));
        if(EasyCollectionUtil.isEmpty(list)){
            return null;
        }
        if(EasyCollectionUtil.isNotSingle(list)){
            throw new EasyQueryInvalidOperationException(String.format("entity class:[%s] field name :[%s] at most one element in PropertyDescriptor array.", EasyClassUtil.getSimpleName(entityClass),field.getName()));
        }
        PropertyDescriptor first = EasyCollectionUtil.first(list);
        return new PropertyDescriptorResult(field.getName(),first);
    }
}
