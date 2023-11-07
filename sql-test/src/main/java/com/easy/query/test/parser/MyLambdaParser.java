package com.easy.query.test.parser;

import com.easy.query.api4j.lambda.SerializedDescriptor;
import com.easy.query.api4j.parser.LambdaParser;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyStringUtil;
import org.objectweb.asm.ClassReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * create time 2023/11/7 10:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyLambdaParser implements LambdaParser {
    private static final Cache<SerializedDescriptor, String> lambdaCache = new DefaultMemoryCache<>();
    @Override
    public <T> String getPropertyName(Property<T, ?> property) {
        if (property == null) {
            return null;
        }
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);


            //Class.method.method....
            if (serializedLambda.getImplMethodKind() == MethodHandleInfo.REF_invokeStatic) {
                return getPropertyNameByInvokeStatic(property.getClass().getClassLoader(), serializedLambda);
            }
            //Class::method
            return getPropertyNameByInvokeVirtual(serializedLambda);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
        private static String getPropertyNameByInvokeStatic(ClassLoader classLoader, SerializedLambda serializedLambda) {
            SerializedDescriptor serializedDescriptor = new SerializedDescriptor(serializedLambda);

        String propertyName = lambdaCache.get(serializedDescriptor);
        if (propertyName != null) {
            return propertyName;
        }

        String className = serializedLambda.getImplClass();
        String classFilePath = classFilePath(className);
        StringBuilder methodBody = new StringBuilder();
        ExpressionClassVisitor expressionClassVisitor = new ExpressionClassVisitor(methodBody, classLoader, serializedDescriptor);
        try (InputStream classStream = getResourceAsStream(classLoader, classFilePath)) {
            ClassReader reader = new ClassReader(classStream);
            reader.accept(expressionClassVisitor, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            String fullPropertyName = methodBody.toString();
            return lambdaCache.computeIfAbsent(serializedDescriptor, key -> {
                return fullPropertyName;
            });
        } catch (IOException e) {
            throw new RuntimeException("error parsing class file " + classFilePath, e);
        }
    }
        private static InputStream getResourceAsStream(ClassLoader classLoader,
                                                       String path)
                throws FileNotFoundException {
            InputStream stream = classLoader.getResourceAsStream(path);
            if (stream == null)
                throw new FileNotFoundException(path);
            return stream;
        }

    private static String classFilePath(String className) {
        return className.replace('.', '/') + ".class";
    }
    private static String getPropertyNameByInvokeVirtual(SerializedLambda serializedLambda){
        String method = serializedLambda.getImplMethodName();

        String attr = null;
        if (method.startsWith("get")) {
            attr = method.substring(3);
        } else {
            attr = method.substring(2);
        }
        return EasyStringUtil.toLowerCaseFirstOne(attr);
    }
}