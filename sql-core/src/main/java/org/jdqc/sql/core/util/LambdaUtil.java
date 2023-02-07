package org.jdqc.sql.core.util;

import org.jdqc.sql.core.abstraction.lambda.Property;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * Copyright (c) 2021.biaodian.All Rights Reserved
 *
 * @FileName: LambdaUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/7 08:36
 * @Created by xuejiaming
 */
public class LambdaUtil {

    private static <T> String getFunctionName(Property<T, ?> property) {
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
            return sqlManager.getNc().getColName(clazz, StringKit.toLowerCaseFirstOne(attr));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

    }
}
