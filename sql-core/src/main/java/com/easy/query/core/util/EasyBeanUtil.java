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
        String propertyName = columnMetadata.getPropertyName();
        if (entityMetadata.isHasValueObject() && propertyName.contains(".")) {
            Object valueObject = getParentPropertyValue(entity,entityMetadata, columnMetadata.getPropertyName(), false);
            if (valueObject == null) {
                return null;
            }
            return getCurrentPropertyValue(valueObject,columnMetadata);
        }

        return getCurrentPropertyValue(entity,columnMetadata);
    }

    public static Object getCurrentPropertyValue(Object entity, ColumnMetadata columnMetadata){
        Property<Object, ?> propertyGetter = columnMetadata.getGetterCaller();
        return propertyGetter.apply(entity);
    }

//    public static void setCurrentPropertyValue(Object entity, EntityMetadata entityMetadata, String propertyName, Object value) {
//        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//        setPropertyValue(entity, entityMetadata, columnMetadata, value);
//    }

//    public static void setPropertyValue(Object entity, EntityMetadata entityMetadata, ColumnMetadata columnMetadata, Object value) {
//
//        if (entityMetadata.isHasValueObject() && columnMetadata.getPropertyName().contains(".")) {
//            String propertyName = columnMetadata.getPropertyName();
//            Object valueObject = getPropertyValue(entity, entityMetadata, propertyName.substring(0, propertyName.indexOf(".")));
//            setCurrentPropertyValue(valueObject, entityMetadata.getColumnNotNull(propertyName), value);
//            return;
//        }
//        setCurrentPropertyValue(entity, columnMetadata, value);
//    }

    /**
     * 根据当前属性名称获取父类的值,如果父类为null则返回null,如果需要创建父类则会在父类为null时创建父类
     * @param entity
     * @param entityMetadata
     * @param propertyName
     * @param withParents
     * @return
     */
    private static Object getParentPropertyValue(Object entity, EntityMetadata entityMetadata, String propertyName,boolean withParents){
        Object parent=entity;
        if (entityMetadata.isHasValueObject() && propertyName.contains(".")) {
            int fromIndex = 0;
            do{
                //比如address.house.province 先处理address 在处理 .address.house
                fromIndex = propertyName.indexOf(".", fromIndex);
                String parentPropertyName = propertyName.substring(0, fromIndex);
                ColumnMetadata parentColumnMetadata = entityMetadata.getColumnNotNull(parentPropertyName);
                Object parentValue = getCurrentPropertyValue(parent, parentColumnMetadata);
                if(parentValue==null&&withParents){//父级没有并且需要创建父级那么就需要创建父级属性
                    parentValue=parentColumnMetadata.getBeanConstructorCreatorOrNull().get();
                    setCurrentPropertyValue(parent,parentColumnMetadata,parentValue);
                }
                parent=parentValue;
                if(parent==null){
                    break;
                }
                fromIndex++;//下一个判断索引点位

            }while (propertyName.indexOf(".",fromIndex)>-1);
        }
        return parent;
    }
    public static void setPropertyValue(Object entity, EntityMetadata entityMetadata, ColumnMetadata columnMetadata, Object value, boolean withParents) {
        Object parent=entity;
        if (entityMetadata.isHasValueObject() && columnMetadata.getPropertyName().contains(".")) {
            parent = getParentPropertyValue(entity, entityMetadata, columnMetadata.getPropertyName(), withParents);
        }
        setCurrentPropertyValue(parent, columnMetadata, value);
    }
//    public static void setPropertyValueByQuery(Object entity, EntityMetadata entityMetadata, ColumnMetadata columnMetadata, Object value) {
//
//        if (entityMetadata.isHasValueObject() && columnMetadata.getPropertyName().contains(".")) {
//            String propertyName = columnMetadata.getPropertyName();
//            ColumnMetadata valueObjectColumnMetadata = entityMetadata.getColumnNotNull(propertyName.substring(0, propertyName.indexOf(".")));
//            Object valueObject = getPropertyValue(entity, entityMetadata, valueObjectColumnMetadata);
//            if(valueObject==null){//只判断一级
//                valueObject = valueObjectColumnMetadata.getBeanConstructorCreatorOrNull().get();
//                setCurrentPropertyValue(entity, valueObjectColumnMetadata, valueObject);
//            }
//            setCurrentPropertyValue(valueObject, entityMetadata.getColumnNotNull(propertyName), value);
//            return;
//        }
//        setCurrentPropertyValue(entity, columnMetadata, value);
//    }

    /**
     * 设置当前值
     * @param entity
     * @param columnMetadata
     * @param value
     */
    public static void setCurrentPropertyValue(Object entity, ColumnMetadata columnMetadata, Object value) {
        PropertySetterCaller<Object> setterCaller = columnMetadata.getSetterCaller();
        setterCaller.call(entity, value);
    }

    public static FastBean getFastBean(Class<?> entityClass) {
        return FAST_BEAN_FUNCTION.apply(entityClass);
//        return EasyMapUtil.computeIfAbsent(CLASS_PROPERTY_FAST_BEAN_CACHE,entityClass, key->new FastBean(entityClass));
    }
}
