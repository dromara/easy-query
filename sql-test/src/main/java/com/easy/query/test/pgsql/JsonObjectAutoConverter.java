package com.easy.query.test.pgsql;

import com.easy.query.core.basic.extension.conversion.ValueAutoConverter;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyMapUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.cache.JsonUtil;
import com.fasterxml.jackson.databind.JavaType;
import org.jetbrains.annotations.NotNull;
import org.postgresql.util.PGobject;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2026/3/13 10:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class JsonObjectAutoConverter implements ValueAutoConverter<Object, Object> {
    private static final Map<ColumnMetadata, JavaType> cacheMap = new ConcurrentHashMap<>();

    @Override
    public boolean apply(@NotNull Class<?> entityClass, @NotNull Class<Object> propertyType, String property) {
        return FieldUtil.isJsonObjectOrArray(entityClass, propertyType, property);
    }

    @Override
    public Object serialize(Object o, @NotNull ColumnMetadata columnMetadata) {
        if (o == null) {
            return null;
        }
        return JsonUtil.object2JsonStr(o);
    }

    @Override
    public Object deserialize(Object s, @NotNull ColumnMetadata columnMetadata) {
        if (s instanceof PGobject) {
            String value = ((PGobject) s).getValue();
            if (EasyStringUtil.isBlank(value)) {
                return null;
            }

            JavaType filedType = getFiledType(columnMetadata);

            return JsonUtil.jsonStr2Object(value, filedType);
        }
        throw new UnsupportedOperationException("not support");
    }


    private JavaType getFiledType(ColumnMetadata columnMetadata) {
        return EasyMapUtil.computeIfAbsent(cacheMap, columnMetadata, key -> {
            return getFiledType0(key);
        });
    }

    private JavaType getFiledType0(ColumnMetadata columnMetadata) {
        Class<?> entityClass = columnMetadata.getEntityMetadata().getEntityClass();
        Field declaredField = EasyClassUtil.getFieldByName(entityClass, columnMetadata.getPropertyName());
        return JsonUtil.jsonMapper.getTypeFactory()
                .constructType(declaredField.getGenericType());
    }
}
