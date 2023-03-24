package com.easy.query.core.util;

import com.easy.query.core.expression.lambda.Property;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 *
 * @FileName: LambdaUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/7 08:36
 * @author xuejiaming
 */
public class LambdaUtil {

    private LambdaUtil(){}
    public static <T> String getPropertyName(Property<T, ?> property) {
        try {
            //添加缓存
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
            return StringUtil.toLowerCaseFirstOne(attr);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
