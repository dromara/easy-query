package com.easy.query.core.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * create time 2025/1/29 23:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyStaticMethodUtil {

    /**
     * 调用静态方法
     * @param clazz          目标类的 Class 对象
     * @param methodName     静态方法名
     * @param parameterTypes 参数类型的全限定类名字符串数组（如 ["int", "java.lang.String"]）
     * @param args           实际参数值数组（需与 parameterTypes 顺序一致）
     * @return 方法调用的返回值
     */
    public static Object invokeStaticMethod(
            Class<?> clazz,
            String methodName,
            String[] parameterTypes,
            Object[] args, Consumer<Exception> exceptionConsumer) {

        try {

            // 1. 将参数类型字符串转换为 Class 对象数组
            Class<?>[] paramClasses = new Class<?>[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                paramClasses[i] = parseType(parameterTypes[i]);
            }

            // 2. 获取方法对象
            Method method = clazz.getDeclaredMethod(methodName, paramClasses);
            method.setAccessible(true); // 确保可访问私有方法

            // 3. 调用静态方法（静态方法无需实例，第一个参数传 null）
            return method.invoke(null, args);
        }catch (Exception ex){
            ex.printStackTrace();
            exceptionConsumer.accept(ex);
            return null;
        }
    }

    /**
     * 将类型字符串解析为 Class 对象
     * @param typeStr 类型字符串（如 "int", "java.lang.String[]"）
     */
    private static Class<?> parseType(String typeStr) throws ClassNotFoundException {
        // 处理基本数据类型
        switch (typeStr) {
            case "byte":    return byte.class;
            case "short":   return short.class;
            case "int":     return int.class;
            case "long":    return long.class;
            case "float":   return float.class;
            case "double":  return double.class;
            case "char":    return char.class;
            case "boolean": return boolean.class;
            case "void":    return void.class;
        }

        // 处理数组类型（如 "java.lang.String[]"）
        if (typeStr.endsWith("[]")) {
            String elementType = typeStr.substring(0, typeStr.length() - 2);
            return Class.forName("[L" + elementType + ";"); // 数组的签名格式
        }

        // 其他引用类型直接加载
        return Class.forName(typeStr);
    }

    // 测试示例
    public static void main(String[] args) throws Exception {
        // 示例：调用 Integer.parseInt(String)
        Object result = invokeStaticMethod(
                Integer.class,
                "parseInt",
                new String[]{"java.lang.String"},
                new Object[]{"123"},
                c->{}
        );
        System.out.println(result); // 输出 123 (int 类型)

        // 示例：调用 Arrays.asList(T...)
        Object list = invokeStaticMethod(
                java.util.Arrays.class,
                "asList",
                new String[]{"java.lang.Object[]"},
                new Object[]{new String[]{"A", "B"}},
                c->{}
        );
        System.out.println(list); // 输出 [A, B]
    }
}
