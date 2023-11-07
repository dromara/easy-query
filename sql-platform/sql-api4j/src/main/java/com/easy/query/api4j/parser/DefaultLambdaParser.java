package com.easy.query.api4j.parser;

import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * create time 2023/11/7 10:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultLambdaParser implements LambdaParser{
    @Override
    public <T> String getPropertyName(Property<T, ?> property) {
        if (property == null) {
            return null;
        }
        try {
            Method declaredMethod = property.getClass().getDeclaredMethod("writeReplace");
            declaredMethod.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(property);


//            //Class.method.method....
//            if (serializedLambda.getImplMethodKind() == MethodHandleInfo.REF_invokeStatic) {
//                return getPropertyNameByInvokeStatic(property.getClass().getClassLoader(), serializedLambda);
//            }
            //Class::method
            return getPropertyNameByInvokeVirtual(serializedLambda);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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
