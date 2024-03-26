package com.easy.query.api.proxy.util;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author xuejiaming
 * @FileName: LambdaUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/7 08:36
 */
public class EasyPropertyLambdaUtil {

    private EasyPropertyLambdaUtil() {
    }

    public static <T> String getPropertyName(Property<T, ?> property) {
        if (property == null) {
            return null;
        }
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);
            String method = serializedLambda.getImplMethodName();


            String attr = null;
            if (method.startsWith("get")) {
                attr = method.substring(3);
            } else {
                attr = method.substring(2);
            }
            return EasyStringUtil.toLowerCaseFirstOne(attr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
