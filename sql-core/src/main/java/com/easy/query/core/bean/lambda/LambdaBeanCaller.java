//package com.easy.query.core.bean.lambda;
//
//import com.easy.query.core.bean.BeanCaller;
//import com.easy.query.core.exception.EasyQueryException;
//import com.easy.query.core.expression.lambda.Property;
//import com.easy.query.core.expression.lambda.PropertySetterCaller;
//import com.easy.query.core.expression.lambda.PropertyVoidSetter;
//import com.easy.query.core.util.EasyClassUtil;
//
//import java.beans.PropertyDescriptor;
//import java.lang.invoke.CallSite;
//import java.lang.invoke.LambdaMetafactory;
//import java.lang.invoke.MethodHandle;
//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.MethodType;
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * create time 2023/6/12 20:33
// * 文件说明
// *
// * @author xuejiaming
// */
//public class LambdaBeanCaller implements BeanCaller {
//    private static final int FLAG_SERIALIZABLE = 1;
//    private final Class<?> beanClass;
//    private final Map<String, PropertySetterCaller<Object>> propertySetterCache;
//    private final Map<String, Property<Object, ?>> propertyGetterCache;
//    public LambdaBeanCaller(Class<?> beanClass) {
//
//        this.beanClass = beanClass;
//        this.propertySetterCache = new ConcurrentHashMap<>();
//        this.propertyGetterCache = new ConcurrentHashMap<>();
//    }
//
//    @Override
//    public Class<?> getBeanClass() {
//        return beanClass;
//    }
//
//    @Override
//    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
//        return propertyGetterCache.computeIfAbsent(prop.getName(), k -> getLambdaProperty(prop));
//    }
//
//    private Property<Object, ?> getLambdaProperty(PropertyDescriptor prop) {
//        Class<?> propertyType = prop.getPropertyType();
//        Method readMethod = prop.getReadMethod();
//        String getFunName = readMethod.getName();
//        final MethodHandles.Lookup caller = MethodHandles.lookup();
//        MethodType methodType = MethodType.methodType(propertyType, beanClass);
//        final CallSite site;
//
//        try {
//            site = LambdaMetafactory.altMetafactory(caller,
//                    "apply",
//                    MethodType.methodType(Property.class),
//                    methodType.erase().generic(),
//                    caller.findVirtual(beanClass, getFunName, MethodType.methodType(propertyType)),
//                    methodType, FLAG_SERIALIZABLE);
//            return (Property<Object, ?>) site.getTarget().invokeExact();
//        } catch (Throwable e) {
//            throw new EasyQueryException(e);
//        }
//    }
//
//    @Override
//    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop) {
//        return propertySetterCache.computeIfAbsent(prop.getName(), key -> getLambdaPropertySetter(prop));
//    }
//    private PropertySetterCaller<Object> getLambdaPropertySetter(PropertyDescriptor prop) {
//        Class<?> propertyType = prop.getPropertyType();
//        MethodHandles.Lookup caller = MethodHandles.lookup();
//        Method writeMethod = EasyClassUtil.getWriteMethodNotNull(prop, beanClass);
//        MethodType setter = MethodType.methodType(writeMethod.getReturnType(), propertyType);
//
//        String getFunName = writeMethod.getName();
//        try {
//
//            //(bean,value)->{bean.set(value)}
//            MethodType instantiatedMethodType = MethodType.methodType(void.class,beanClass, propertyType);
//            MethodHandle target = caller.findVirtual(beanClass, getFunName, setter);
//            MethodType samMethodType = MethodType.methodType(void.class, Object.class, Object.class);
//            CallSite site = LambdaMetafactory.metafactory(
//                    caller,
//                    "apply",
//                    MethodType.methodType(PropertyVoidSetter.class),
//                    samMethodType,
//                    target,
//                    instantiatedMethodType
//            );
//
//            PropertyVoidSetter<Object,Object> objectPropertyVoidSetter = (PropertyVoidSetter<Object,Object>) site.getTarget().invokeExact();
//            return objectPropertyVoidSetter::apply;
//        } catch (Throwable e) {
//            throw new EasyQueryException(e);
//        }
//    }
//}
