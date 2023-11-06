package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: BeanUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/18 22:09
 */
public class EasyBeanUtil {


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
        String propertyName = columnMetadata.getPropertyName();
        if (entityMetadata.isHasValueObject() && propertyName.contains(".")) {
            Object valueObject = getPropertyValue(entity, entityMetadata, propertyName.substring(0, propertyName.indexOf(".")));
            if (valueObject == null) {
                return null;
            }
            Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();
            return propertyGetter.apply(valueObject);
        }
        Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();

        return propertyGetter.apply(entity);
    }

    public static void setPropertyValue(Object entity, EntityMetadata entityMetadata, String propertyName, Object value) {
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
        setPropertyValue(entity, entityMetadata, columnMetadata, value);
    }

    public static void setPropertyValue(Object entity, EntityMetadata entityMetadata, ColumnMetadata columnMetadata, Object value) {
        String propertyName = columnMetadata.getPropertyName();
        if (entityMetadata.isHasValueObject() && propertyName.contains(".")) {
            Object valueObject = getPropertyValue(entity, entityMetadata, propertyName.substring(0, propertyName.indexOf(".")));
            setPropertyValue(valueObject, entityMetadata.getColumnNotNull(propertyName), value);
            return;
        }
        setPropertyValue(entity, columnMetadata, value);
    }

    public static void setPropertyValue(Object entity, ColumnMetadata columnMetadata, Object value) {
        PropertySetterCaller<Object> setterCaller = columnMetadata.getSetterCaller();
        setterCaller.call(entity, value);
    }

    public static FastBean getFastBean(Class<?> entityClass) {
        return new FastBean(entityClass);
//        return EasyMapUtil.computeIfAbsent(CLASS_PROPERTY_FAST_BEAN_CACHE,entityClass, key->new FastBean(entityClass));
    }
}
