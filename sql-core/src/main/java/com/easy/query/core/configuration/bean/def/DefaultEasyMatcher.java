package com.easy.query.core.configuration.bean.def;

import com.easy.query.core.configuration.bean.EasyMatcher;
import com.easy.query.core.configuration.bean.PropertyDescriptorResult;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.logging.nologging.NoLoggingImpl;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.PropertyDescriptorFinder;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

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
public class DefaultEasyMatcher implements EasyMatcher {
    private static final Log log = LogFactory.getLog(DefaultEasyMatcher.class);
    private final PropertyDescriptorFinder propertyDescriptorFinder;
    public DefaultEasyMatcher(PropertyDescriptor[] propertyDescriptors){
        this.propertyDescriptorFinder = new PropertyDescriptorFinder(propertyDescriptors);
    }
    @Override
    public PropertyDescriptorResult match(Class<?> entityClass, Field field) {
        String property = EasyStringUtil.toLowerCaseFirstOne(field.getName());
        //未找到bean属性就直接忽略
        PropertyDescriptor propertyDescriptor = propertyDescriptorFinder.find(property);
        if (propertyDescriptor == null) {
            propertyDescriptor = propertyDescriptorFinder.findIgnoreCase(property);
            if (propertyDescriptor != null) {
                String warningMessage = EasyClassUtil.getSimpleName(entityClass) + " filed:" + field.getName() + ",compare name:" + property + ",property name:" + propertyDescriptor.getName() +" If you want to use a non-standard Java bean, please set [propertyMode] to [same_as_entity].";
                if (log instanceof NoLoggingImpl) {
                    System.out.println("NoLogging:" + warningMessage);
                } else {
                    log.warn(warningMessage);
                }
            }
            return null;
        }
        return new PropertyDescriptorResult(property,propertyDescriptor);
    }
}
