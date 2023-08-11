package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * @FileName: BeanUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/18 22:09
 * @author xuejiaming
 */
public class EasyBeanUtil {


    public static Set<String> getBeanMatchProperties(EntityMetadataManager entityMetadataManager, Object entity, Predicate<Object> propertyPredicate){

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Collection<String> properties = entityMetadata.getProperties();
        LinkedHashSet<String> matchProperties = new LinkedHashSet<>(properties.size());
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();

            Object value = propertyGetter.apply(entity);
            if(propertyPredicate.test(value)){
                matchProperties.add(propertyName);
            }
        }
        return matchProperties;
    }

    public static FastBean getFastBean(Class<?> entityClass) {
        return new FastBean(entityClass);
//        return EasyMapUtil.computeIfAbsent(CLASS_PROPERTY_FAST_BEAN_CACHE,entityClass, key->new FastBean(entityClass));
    }
}
