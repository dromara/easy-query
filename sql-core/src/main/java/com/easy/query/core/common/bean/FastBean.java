package com.easy.query.core.common.bean;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetter;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.PropertyVoidSetter;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;

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

    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
        return getBeanGetter(prop.getName(),prop.getPropertyType());
    }

    public Property<Object, ?> getBeanGetter(String propertyName, Class<?> propertyType) {
        return propertyGetterCache.computeIfAbsent(propertyName, k -> getLambdaProperty(propertyName, propertyType));
    }

    private Property<Object, ?> getLambdaProperty(String propertyName, Class<?> fieldType) {
        final MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(fieldType, beanClass);
        final CallSite site;

        String getFunName = "get" + StringUtil.toUpperCaseFirstOne(propertyName);
        try {
            site = LambdaMetafactory.altMetafactory(caller,
                    "apply",
                    MethodType.methodType(Property.class),
                    methodType.erase(),
                    caller.findVirtual(beanClass, getFunName, MethodType.methodType(fieldType)),
                    methodType, FLAG_SERIALIZABLE);
            return (Property<Object, ?>) site.getTarget().invokeExact();
        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }

    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop) {
        return propertySetterCache.computeIfAbsent(prop.getName(), key -> getLambdaPropertySetter(prop));
//        return getBeanSetter(prop,true);
    }

    //    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop,boolean cache){
//        if(cache){
//        }
//        return getLambdaPropertySetter( prop);
//    }
    private PropertySetterCaller<Object> getLambdaPropertySetter(PropertyDescriptor prop) {
        String propertyName = prop.getName();
        Class<?> propertyType = prop.getPropertyType();
        MethodHandles.Lookup caller = MethodHandles.lookup();
        Method writeMethod = ClassUtil.getWriteMethodNotNull(prop, beanClass);
        MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);

        String getFunName = "set" + StringUtil.toUpperCaseFirstOne(propertyName);
        try {


            MethodHandle target = caller.findVirtual(beanClass, getFunName, setter);
            MethodType func = target.type();
            if (void.class.equals(writeMethod.getReturnType())) {
                CallSite site = LambdaMetafactory.metafactory(
                        caller,
                        "apply",
                        MethodType.methodType(PropertyVoidSetter.class),
                        func.erase(),
                        target,
                        func
                );

                PropertyVoidSetter<Object> objectPropertyVoidSetter = (PropertyVoidSetter<Object>) site.getTarget().invokeExact();
                return objectPropertyVoidSetter::apply;

            } else {
                CallSite site = LambdaMetafactory.metafactory(
                        caller,
                        "apply",
                        MethodType.methodType(PropertySetter.class),
                        func.erase(),
                        target,
                        func
                );

                PropertySetter<Object, ?> objectPropertySetter = (PropertySetter<Object, ?>) site.getTarget().invokeExact();
                return objectPropertySetter::apply;
            }

        } catch (Throwable e) {
            throw new EasyQueryException(e);
        }
    }
}
