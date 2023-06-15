package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.bean.BasicEntityMetadata;
import com.easy.query.core.metadata.bean.BasicEntityMetadataManager;

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
        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
        for (String propertyName : properties) {
            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(propertyName);

            Object value = propertyGetter.apply(entity);
            if(propertyPredicate.test(value)){
                matchProperties.add(propertyName);
            }
        }
        return matchProperties;
    }
    private static final Map<Class<?>, FastBean> CLASS_PROPERTY_FAST_BEAN_CACHE = new ConcurrentHashMap<>();

    public static FastBean getFastBean(Class<?> entityClass) {
        FastBean fastBean = CLASS_PROPERTY_FAST_BEAN_CACHE.get(entityClass);
        if(fastBean!=null){
            return fastBean;
        }
        BasicEntityMetadata basicEntityMetadata = BasicEntityMetadataManager.getBasicEntityMetadata(entityClass);
        FastBean fastBeanResult = new FastBean(basicEntityMetadata);
        CLASS_PROPERTY_FAST_BEAN_CACHE.putIfAbsent(entityClass,fastBeanResult);
        return fastBeanResult;
    }
}
