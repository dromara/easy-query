package com.easy.query.core.common.bean;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.expression.lambda.PropertyVoidSetter;
import com.easy.query.core.util.EasyClassUtil;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * create time 2023/3/28 17:20
 * 用来快速实现bean对象的get和set方法，
 * 直接获取get方法的lambda基本是零开销调用
 * set也是零开销调用，除了第一次需要构建lambda和缓存
 *
 * @author xuejiaming
 */
public interface FastBean {

//    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
//        return getBeanGetter(prop.getName(),prop.getPropertyType());
//    }

   Property<Object, ?> getBeanGetter(FastBeanProperty prop);


   PropertySetterCaller<Object> getBeanSetter(FastBeanProperty prop);

   Supplier<Object> getBeanConstructorCreator();
}
