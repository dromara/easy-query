//package com.easy.query.core.bean.reflect;
//
//import com.easy.query.core.bean.BeanCaller;
//import com.easy.query.core.exception.EasyQueryException;
//import com.easy.query.core.expression.lambda.Property;
//import com.easy.query.core.expression.lambda.PropertySetterCaller;
//import com.easy.query.core.util.EasyClassUtil;
//
//import java.beans.PropertyDescriptor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
///**
// * create time 2023/6/12 20:55
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ReflectBeanCaller implements BeanCaller {
//    private final Class<?> beanClass;
//
//    public ReflectBeanCaller(Class<?> beanClass){
//
//        this.beanClass = beanClass;
//    }
//
//    @Override
//    public Class<?> getBeanClass() {
//        return beanClass;
//    }
//
//    @Override
//    public Property<Object, ?> getBeanGetter(PropertyDescriptor prop) {
//        return  getReflectGetProperty(prop);
////        return bean->null;
//    }
//    public Property<Object, ?> getReflectGetProperty(PropertyDescriptor prop) {
//        Method readMethod = prop.getReadMethod();
//
//       return bean->{
//           try {
//               return readMethod.invoke(bean);
//           } catch (IllegalAccessException | InvocationTargetException e) {
//               throw new EasyQueryException(e);
//           }
//       };
//    }
//
//    @Override
//    public PropertySetterCaller<Object> getBeanSetter(PropertyDescriptor prop) {
//        return getReflectSetProperty(prop);
//    }
//    public PropertySetterCaller<Object> getReflectSetProperty(PropertyDescriptor prop) {
//        Method setter = EasyClassUtil.getWriteMethodOrNull(prop, beanClass);
//        if(setter==null){
//            return (bean,value)->{};
//        }
//        return (bean,value)->{
//            try {
//                setter.invoke(bean, value);
//            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//                throw new EasyQueryException("Cannot set " + prop.getName() + ": " + e.getMessage());
//            }
//        };
//    }
//}
