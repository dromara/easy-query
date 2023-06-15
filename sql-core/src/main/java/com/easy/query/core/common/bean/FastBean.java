package com.easy.query.core.common.bean;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.PropertyVoidSetter;
import com.easy.query.core.util.EasyClassUtil;

import java.beans.PropertyDescriptor;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/3/28 17:20
 * 用来快速实现bean对象的get和set方法，
 * 直接获取get方法的lambda基本是零开销调用
 * set也是零开销调用，除了第一次需要构建lambda和缓存
 * @author xuejiaming
 */
public class FastBean {
    private static final int FLAG_SERIALIZABLE = 1;
    private final Class<?> beanClass;
    private final Map<String, PropertySetterCaller<Object>> propertySetterCache;
    private final Map<String, Property<Object, ?>> propertyGetterCache;

    public FastBean(Class<?> beanClass) {

        this.beanClass = beanClass;
        this.propertySetterCache = new ConcurrentHashMap<>();
        this.propertyGetterCache = new ConcurrentHashMap<>();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

//    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
//        return getBeanGetter(prop.getName(),prop.getPropertyType());
//    }

    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
        return propertyGetterCache.computeIfAbsent(prop.getName(), k -> getLambdaProperty(prop));
    }

    private Property<Object, ?> getLambdaProperty(PropertyDescriptor prop) {
        Class<?> propertyType = prop.getPropertyType();
        Method readMethod = prop.getReadMethod();
        String getFunName = readMethod.getName();
        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(propertyType, beanClass);
        final CallSite site;

        try {
            site = LambdaMetafactory.altMetafactory(caller,
                    "apply",
                    MethodType.methodType(Property.class),
                    methodType.erase().generic(),
                    caller.findVirtual(beanClass, getFunName, MethodType.methodType(propertyType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<Object, ?>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }

    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop) {
        return propertySetterCache.computeIfAbsent(prop.getName(), key -> getLambdaPropertySetter(prop));
    }
    private PropertySetterCaller<Object> getLambdaPropertySetter(PropertyDescriptor prop) {
        Class<?> propertyType = prop.getPropertyType();
        MethodHandles.Lookup caller = MethodHandles.lookup();
        Method writeMethod = EasyClassUtil.getWriteMethodNotNull(prop, beanClass);
        MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);

        String getFunName = writeMethod.getName();
        try {

            //()->{bean.setxxx(propertyType)}
            MethodType instantiatedMethodType = MethodType.methodType(void.class,beanClass, propertyType);
            MethodHandle target = caller.findVirtual(beanClass, getFunName, setter);
            MethodType samMethodType = MethodType.methodType(void.class, Object.class, Object.class);
            CallSite site = LambdaMetafactory.metafactory(
                    caller,
                    "apply",
                    MethodType.methodType(PropertyVoidSetter.class),
                    samMethodType,
                    target,
                    instantiatedMethodType
            );

            PropertyVoidSetter<Object,Object> objectPropertyVoidSetter = (PropertyVoidSetter<Object,Object>) site.getTarget().invokeExact();
            return objectPropertyVoidSetter::apply;
        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }
}
