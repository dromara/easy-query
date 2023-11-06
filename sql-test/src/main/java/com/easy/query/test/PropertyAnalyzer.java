package com.easy.query.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/11/6 10:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyAnalyzer {
    public static String analyzePropertyPath(Object obj, Object result) {
        if (result == null) {
            return null;
        }

        // 获取所有getter方法
        List<Method> getterMethods = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (isGetter(method)) {
                    getterMethods.add(method);
                }
            }
            clazz = clazz.getSuperclass();
        }

        // 检查哪个getter方法返回了指定的值
        for (Method method : getterMethods) {
            try {
                Object value = method.invoke(obj);
                if (Objects.equals(value, result)) {
                    return method.getName().replace("get", "").toLowerCase();
                }
            } catch (Exception e) {
                // 处理反射异常
            }
        }

        return null;
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
                method.getParameterCount() == 0 &&
                !method.getReturnType().equals(void.class);
    }
}
