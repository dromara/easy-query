package com.easy.query.test.pgsql;

import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyMapUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2026/3/13 19:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class FieldUtil {

    private static final Map<FieldKey, Boolean> cacheMap = new ConcurrentHashMap<>();

    public static boolean isJsonObjectOrArray(Class<?> clazz, Class<?> propertyType, String property) {

        return EasyMapUtil.computeIfAbsent(cacheMap, new FieldKey(clazz, propertyType, property), FieldUtil::isJsonObjectOrArray);
    }

    private static boolean isJsonObjectOrArray(FieldKey fieldKey) {
        if (JsonObject.class.isAssignableFrom(fieldKey.propertyType)) {
            return true;
        }

        if (List.class.isAssignableFrom(fieldKey.propertyType)) {
            Field field = EasyClassUtil.getFieldByName(fieldKey.clazz, fieldKey.property);
            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                if (typeArguments.length > 0) {
                    Type elementType = typeArguments[0];
                    if (elementType instanceof Class) {
                        return JsonObject.class.isAssignableFrom((Class<?>) elementType);
                    }
                }
            }
        }
        return false;
    }


    static class FieldKey {
        private final Class<?> clazz;
        private final Class<?> propertyType;
        private final String property;

        FieldKey(Class<?> clazz, Class<?> propertyType, String property) {
            this.clazz = clazz;
            this.propertyType = propertyType;
            this.property = property;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            FieldKey fieldKey = (FieldKey) o;
            return Objects.equals(clazz, fieldKey.clazz) && Objects.equals(property, fieldKey.property);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, property);
        }
    }
}
