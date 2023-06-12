package com.easy.query.core.util;

import com.easy.query.core.bean.BeanCaller;
import com.easy.query.core.bean.BeanValueCaller;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @FileName: BeanUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/18 22:09
 * @author xuejiaming
 */
public class EasyBeanUtil {


    public static Set<String> getBeanMatchProperties(BeanValueCaller beanValueCaller, EntityMetadataManager entityMetadataManager, Object entity, Predicate<Object> propertyPredicate){

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Collection<String> properties = entityMetadata.getProperties();
        LinkedHashSet<String> matchProperties = new LinkedHashSet<>(properties.size());
        BeanCaller beanCaller = beanValueCaller.getBeanCaller(entityClass);
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            Property<Object, ?> propertyGetter = beanCaller.getBeanGetter(columnMetadata.getProperty());

            Object value = propertyGetter.apply(entity);
            if(propertyPredicate.test(value)){
                matchProperties.add(propertyName);
            }
        }
        return matchProperties;
    }
//    private static final Map<Class<?>, FastBean> CLASS_PROPERTY_FAST_BEAN_CACHE = new ConcurrentHashMap<>();

//    public static FastBean getFastBean(Class<?> entityClass) {
//        return CLASS_PROPERTY_FAST_BEAN_CACHE.computeIfAbsent(entityClass, key -> new FastBean(entityClass));
//    }
}
