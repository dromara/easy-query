package com.easy.query.test;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.MyTopic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * create time 2024/5/25 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class FindMain {

    public static void main(String[] args) {
        ProxyEntityAvailable<?,?> myTopic = new MyTopic();
        Class<?> secondGenericType = getProxyGenericType(MyTopic.class, ProxyEntityAvailable.class);

        System.out.println(secondGenericType); // 打印出B.class
    }

    public static Class<?> getProxyGenericType(Class<?> clazz, Class<?> targetInterface) {

        // 解析实现的接口的泛型参数
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Class<?> result = getGenericTypeFromType((ParameterizedType) genericInterface, targetInterface);
                if (result != null) {
                    return result;
                }
            }
        }

        // 解析父类的泛型参数
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Class<?> result = getGenericTypeFromType((ParameterizedType) superClass, targetInterface);
            if (result != null) {
                return result;
            }
        }


        // 如果父类和接口中没有找到，继续递归解析父类
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            return getProxyGenericType(superclass, targetInterface);
        }

        // 如果递归完毕还是没有找到对应的泛型类型
        throw new IllegalArgumentException("Cannot find the second generic type for " + clazz.getName());
    }

    private static Class<?> getGenericTypeFromType(ParameterizedType type, Class<?> targetInterface) {
        Type rawType = type.getRawType();
        if (rawType instanceof Class) {

            Class<?> rawClass = (Class<?>) rawType;
            if (Objects.equals(targetInterface, rawClass)) {
                Type[] actualTypeArguments = type.getActualTypeArguments();
                if (actualTypeArguments.length > 1 && actualTypeArguments[1] instanceof Class) {
                    return (Class<?>) actualTypeArguments[1];
                }
            }
            if (targetInterface.isAssignableFrom(rawClass)) {

                Type[] genericInterfaces = rawClass.getGenericInterfaces();
                for (Type genericInterface : genericInterfaces) {
                    if (genericInterface instanceof ParameterizedType) {
                        Class<?> result = getGenericTypeFromType((ParameterizedType) genericInterface, targetInterface);
                        if (result != null) {
                            return result;
                        }
                    }
                }

                // 如果没有直接匹配，检查父类和接口
                Class<?> superClass = rawClass.getSuperclass();
                if (superClass != null) {
                    return getProxyGenericType(superClass, targetInterface);
                }

            }
        }

        return null;
    }
}
