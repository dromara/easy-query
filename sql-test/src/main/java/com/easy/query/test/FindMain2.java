package com.easy.query.test;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.MyTopic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2024/5/25 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class FindMain2 {

    public static void main(String[] args) {
        try {
            Class<?> secondGenericType = getProxyGenericType(MyTopic.class, ProxyEntityAvailable.class);
            System.out.println(secondGenericType); // 应该打印 A.class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getProxyGenericType(Class<?> clazz, Class<?> targetInterface) throws ClassNotFoundException {
        Map<TypeVariable<?>, Type> typeMap = new HashMap<>();
        return getGenericType(clazz, targetInterface, typeMap);
    }

    private static Class<?> getGenericType(Class<?> clazz, Class<?> targetInterface, Map<TypeVariable<?>, Type> typeMap) throws ClassNotFoundException {
        // 解析当前类的父类
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            resolveTypeArguments((ParameterizedType) superClass, typeMap);
        }

        // 解析当前类实现的接口
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (parameterizedType.getRawType().equals(targetInterface)) {
                    return getClassForType(parameterizedType.getActualTypeArguments()[1], typeMap);
                } else {
                    resolveTypeArguments(parameterizedType, typeMap);
                }
            }
        }

        // 递归解析父类
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            return getGenericType(superclass, targetInterface,typeMap);
        }

        throw new ClassNotFoundException("Cannot find the second generic type for " + clazz.getName());
    }

    private static void resolveTypeArguments(ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> typeMap) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<?>[] typeVariables = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();
        for (int i = 0; i < typeVariables.length; i++) {
            typeMap.put(typeVariables[i], actualTypeArguments[i]);
        }
    }

    private static Class<?> getClassForType(Type type, Map<TypeVariable<?>, Type> typeMap) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof TypeVariable<?>) {
            Type resolvedType = typeMap.get(type);
            if (resolvedType instanceof Class<?>) {
                return (Class<?>) resolvedType;
            } else {
                return getClassForType(resolvedType, typeMap);
            }
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        }
        throw new IllegalArgumentException("Unable to resolve type to a class: " + type);
    }
}
