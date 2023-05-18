package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * @FileName: BeanUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/18 22:09
 * @author xuejiaming
 */
public class EasyBeanUtil {

    private static final Log log = LogFactory.getLog(EasyBeanUtil.class);

//    /**
//     * 将源对象的属性值复制到目标对象中
//     *
//     * @param source 源对象
//     * @param target 目标对象
//     * @param propertyNameSet 哪些属性需要拷贝
//     */
//    public static void copyProperties(Object source, Object target, Set<String> propertyNameSet) {
//        Class<?> sourceClass = source.getClass();
//        Class<?> targetClass = target.getClass();
//
//        List<Field> fields = ClassUtil.getAllFields(sourceClass);
//        for (Field field : fields) {
//            if (propertyNameSet != null && !propertyNameSet.contains(field.getName())) {
//                continue;
//            }
//            try {
//                PropertyDescriptor sourcePd = new PropertyDescriptor(field.getName(), sourceClass);
//                Method sourceGetMethod = sourcePd.getReadMethod();
//                PropertyDescriptor targetPd = new PropertyDescriptor(field.getName(), targetClass);
//                Method targetSetMethod = targetPd.getWriteMethod();
//                if (sourceGetMethod != null && targetSetMethod != null) {
//                    Object value = sourceGetMethod.invoke(source);
//                    if (value != null) {
//                        Class<?> fieldClass = field.getType();
//                        if (isPrimitive(fieldClass)) {
//                            targetSetMethod.invoke(target, value);
//                        } else if (fieldClass == LocalDateTime.class) {
//                            if (value instanceof Date) {
//                                LocalDateTime newValue = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//                                targetSetMethod.invoke(target, newValue);
//                            } else if (value instanceof LocalDate) {
//                                LocalDate localDate = (LocalDate) value;
//                                LocalDate newValue = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
//                                targetSetMethod.invoke(target, newValue);
//                            } else {
//                                LocalDateTime newValue = LocalDateTime.from((LocalDateTime) value);
//                                targetSetMethod.invoke(target, newValue);
//                            }
//                        } else if (fieldClass.isArray()) {
//                            Object[] sourceArray = (Object[]) value;
//                            Class<?> fieldType = fieldClass.getComponentType();
//                            if (isPrimitive(fieldType)) {
//                                targetSetMethod.invoke(target, value);
//                            } else {
//                                Object[] targetArray = (Object[]) fieldClass.newInstance();
//                                for (int i = 0; i < sourceArray.length; i++) {
//                                    Object sourceValue = sourceArray[i];
//                                    Object targetValue = fieldType.newInstance();
//                                    copyProperties(sourceValue, targetValue,null);
//                                    targetArray[i] = targetValue;
//                                }
//                                targetSetMethod.invoke(target, targetArray);
//                            }
//                        } else if (Collection.class.isAssignableFrom(fieldClass)) {
//                            Collection<Object> sourceCollection = (Collection<Object>) value;
//                            Collection<Object> targetCollection = (Collection<Object>) fieldClass.newInstance();
//                            Class<?> fieldType = null;
//                            for (Object sourceValue : sourceCollection) {
//                                if (fieldType == null) {
//                                    fieldType = sourceValue.getClass();
//                                }
//                                Object targetValue = fieldType.newInstance();
//                                copyProperties(sourceValue, targetValue,null);
//                                targetCollection.add(targetValue);
//                            }
//                            targetSetMethod.invoke(target, targetCollection);
//                        } else if (Map.class.isAssignableFrom(fieldClass)) {
//                            Map<Object, Object> sourceMap = (Map<Object, Object>) value;
//                            Map<Object, Object> targetMap = (Map<Object, Object>) fieldClass.newInstance();
//                            Class<?> keyType = null;
//                            Class<?> valueType = null;
//                            for (Map.Entry<?, ?> entry : sourceMap.entrySet()) {
//                                if (keyType == null) {
//                                    keyType = entry.getKey().getClass();
//                                }
//                                if (valueType == null) {
//                                    valueType = entry.getValue().getClass();
//                                }
//                                Object targetKey = keyType.newInstance();
//                                Object targetValue = valueType.newInstance();
//                                copyProperties(entry.getKey(), targetKey,null);
//                                copyProperties(entry.getValue(), targetValue,null);
//                                targetMap.put(targetKey, targetValue);
//                            }
//                            targetSetMethod.invoke(target, targetMap);
//                        } else {
//                            Object targetValue = fieldClass.newInstance();
//                            copyProperties(value, targetValue,null);
//                            targetSetMethod.invoke(target, targetValue);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                log.error(ClassUtil.getSimpleName(sourceClass) + " copy properties error." + e.getMessage(), e);
//            }
//        }
//    }

    /**
     * 判断一个类是否为基本数据类型
     *
     * @param clazz 类型
     * @return 是否为基本数据类型
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == String.class || clazz == Boolean.class || clazz == Character.class
                || Number.class.isAssignableFrom(clazz);
    }


    public static Set<String> getBeanMatchProperties(EntityMetadataManager entityMetadataManager, Object entity, Predicate<Object> propertyPredicate){

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Collection<String> properties = entityMetadata.getProperties();
        LinkedHashSet<String> matchProperties = new LinkedHashSet<>(properties.size());
        FastBean fastBean = getFastBean(entityClass);
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(columnMetadata.getProperty());

            Object value = propertyGetter.apply(entity);
            if(propertyPredicate.test(value)){
                matchProperties.add(propertyName);
            }
        }
        return matchProperties;
    }
    private static final Map<Class<?>, FastBean> CLASS_PROPERTY_FAST_BEAN_CACHE = new ConcurrentHashMap<>();

    public static FastBean getFastBean(Class<?> entityClass) {
        return CLASS_PROPERTY_FAST_BEAN_CACHE.computeIfAbsent(entityClass, key -> new FastBean(entityClass));
    }
    public static Property<Object, ?> getPropertyGetterLambda(Class<?> entityClass, String propertyName, Class<?> fieldType) {
        return getFastBean(entityClass).getBeanGetter(propertyName, fieldType);
    }
    public static PropertySetterCaller<Object> getPropertySetterLambda(Class<?> entityClass, PropertyDescriptor prop) {
        return getFastBean(entityClass).getBeanSetter(prop);
    }
}
