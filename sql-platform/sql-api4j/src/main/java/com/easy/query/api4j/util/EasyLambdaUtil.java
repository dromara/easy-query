package com.easy.query.api4j.util;

import com.easy.query.api4j.lambda.SerializedDescriptor;
import com.easy.query.api4j.parser.DefaultLambdaParser;
import com.easy.query.api4j.parser.LambdaParser;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.expression.lambda.Property;

import java.util.Objects;

/**
 * @author xuejiaming
 * @FileName: LambdaUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/7 08:36
 */
public class EasyLambdaUtil {
    private static LambdaParser lambdaParser;

    static {
        lambdaParser = new DefaultLambdaParser();
    }

    private static final Cache<SerializedDescriptor, String> lambdaCache = new DefaultMemoryCache<>();

    private EasyLambdaUtil() {
    }

    public static void replaceParser(LambdaParser p) {
        Objects.requireNonNull(p, "replaceParser p is null");
        lambdaParser = p;
    }

    public static <T> String getPropertyName(Property<T, ?> property) {
        return lambdaParser.getPropertyName(property);
//        if (property == null) {
//            return null;
//        }
//        try {
//            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
//            declaredMethod.setAccessible(Boolean.TRUE);
//            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
//
//
//            //Class.method.method....
//            if (serializedLambda.getImplMethodKind() == MethodHandleInfo.REF_invokeStatic) {
//                return getPropertyNameByInvokeStatic(property.getClass().getClassLoader(), serializedLambda);
//            }
//            //Class::method
//            return getPropertyNameByInvokeVirtual(serializedLambda);
//
//        } catch (ReflectiveOperationException e) {
//            throw new RuntimeException(e);
//        }
    }

//    private static String getPropertyNameByInvokeVirtual(SerializedLambda serializedLambda) {
//        String method = serializedLambda.getImplMethodName();
//
//        String attr = null;
//        if (method.startsWith("get")) {
//            attr = method.substring(3);
//        } else {
//            attr = method.substring(2);
//        }
//        return EasyStringUtil.toLowerCaseFirstOne(attr);
//    }
//
//    private static String getPropertyNameByInvokeStatic(ClassLoader classLoader, SerializedLambda serializedLambda) {
//        SerializedDescriptor serializedDescriptor = new SerializedDescriptor(serializedLambda);
//
//        String propertyName = lambdaCache.get(serializedDescriptor);
//        if (propertyName != null) {
//            return propertyName;
//        }
//
//        String className = serializedLambda.getImplClass();
//        String classFilePath = classFilePath(className);
//        StringBuilder methodBody = new StringBuilder();
//        ExpressionClassVisitor expressionClassVisitor = new ExpressionClassVisitor(methodBody, classLoader, serializedDescriptor);
//        try (InputStream classStream = getResourceAsStream(classLoader, classFilePath)) {
//            ClassReader reader = new ClassReader(classStream);
//            reader.accept(expressionClassVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
//            String fullPropertyName = methodBody.toString();
//            return lambdaCache.computeIfAbsent(serializedDescriptor, key -> {
//                return fullPropertyName;
//            });
//        } catch (IOException e) {
//            throw new RuntimeException("error parsing class file " + classFilePath, e);
//        }
//    }
//
//    public static <T> String getPropertyName2(Property<T, ?> property) {
//        if (property == null) {
//            return null;
//        }
//        try {
//            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
//            declaredMethod.setAccessible(Boolean.TRUE);
//            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
//
//            switch (serializedLambda.getImplMethodKind()) {
//                //Class::method
//                case MethodHandleInfo.REF_invokeVirtual:
//                    //Class.method.method....
//                case MethodHandleInfo.REF_invokeStatic:
//                    return getPropertyNameByInvokeStatic(property.getClass().getClassLoader(), serializedLambda);
//            }
//            return getPropertyNameByInvokeVirtual(serializedLambda);
//
//        } catch (ReflectiveOperationException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static InputStream getResourceAsStream(ClassLoader classLoader,
//                                                   String path)
//            throws FileNotFoundException {
//        InputStream stream = classLoader.getResourceAsStream(path);
//        if (stream == null)
//            throw new FileNotFoundException(path);
//        return stream;
//    }
//
//    private static String classFilePath(String className) {
//        return className.replace('.', '/') + ".class";
//    }
}
