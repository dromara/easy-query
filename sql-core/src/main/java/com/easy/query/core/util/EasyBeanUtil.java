package com.easy.query.core.util;

import com.easy.query.core.common.bean.DefaultFastBean;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: BeanUtil.java
 * @Description: 文件说明
 * create time 2023/3/18 22:09
 */
public class EasyBeanUtil {
    public static Function<Class<?>,FastBean> FAST_BEAN_FUNCTION= DefaultFastBean::new;

    public static Set<String> getBeanMatchProperties(EntityMetadataManager entityMetadataManager, Object entity, Predicate<Object> propertyPredicate) {

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Collection<String> properties = entityMetadata.getProperties();
        LinkedHashSet<String> matchProperties = new LinkedHashSet<>(properties.size());
        for (String propertyName : properties) {
            Object value = getPropertyValue(entity, entityMetadata, propertyName);
//            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//            Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();
//
//            Object value = propertyGetter.apply(entity);
            if (propertyPredicate.test(value)) {
                matchProperties.add(propertyName);
            }
        }
        return matchProperties;
    }

    public static Object getPropertyValue(Object entity, EntityMetadata entityMetadata, String propertyName) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        return getPropertyValue(entity, entityMetadata, columnMetadata);
    }

    public static Object getPropertyValue(Object entity, EntityMetadata entityMetadata, ColumnMetadata columnMetadata) {
        return getCurrentPropertyValue(entity,columnMetadata);
    }

    public static Object getCurrentPropertyValue(Object entity, ColumnMetadata columnMetadata){
        Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();
        return propertyGetter.apply(entity);
    }


    /**
     * 设置当前值
     * @param entity
     * @param columnMetadata
     * @param value
     */
    public static void setPropertyValue(Object entity, ColumnMetadata columnMetadata, Object value) {
        PropertySetterCaller<Object> setterCaller = columnMetadata.getSetterCaller();
        setterCaller.call(entity, value);
    }

    public static FastBean getFastBean(Class<?> entityClass) {
        return FAST_BEAN_FUNCTION.apply(entityClass);
//        return EasyMapUtil.computeIfAbsent(CLASS_PROPERTY_FAST_BEAN_CACHE,entityClass, key->new FastBean(entityClass));
    }
}
