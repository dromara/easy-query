package com.easy.query.core.common.bean;

import com.easy.query.core.exception.EasyQueryException;
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
 * 文件说明
 *
 * @author xuejiaming
 */
public class BeanFastSetter {
    private final Class<?> beanClass;
    private final  Map<String, PropertySetterCaller<Object>> propertyCache;

    public BeanFastSetter(Class<?> beanClass){

        this.beanClass = beanClass;
        this.propertyCache=new ConcurrentHashMap<>();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop){
        return getBeanSetter(prop,true);
    }

    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop,boolean cache){
        if(cache){
            return propertyCache.computeIfAbsent(prop.getName(), key -> getLambdaPropertySetter( prop));
        }
        return getLambdaPropertySetter( prop);
    }
    private PropertySetterCaller<Object> getLambdaPropertySetter(PropertyDescriptor prop) {
        String propertyName = prop.getName();
        Class<?> propertyType = prop.getPropertyType();

        String getFunName = "set" + StringUtil.toUpperCaseFirstOne(propertyName);
        try {

            MethodHandles.Lookup caller = MethodHandles.lookup();
            Method writeMethod = ClassUtil.getWriteMethodNotNull(prop, beanClass);

            MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);
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
