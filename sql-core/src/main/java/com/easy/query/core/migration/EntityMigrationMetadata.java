package com.easy.query.core.migration;

import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyBase64Util;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStaticMethodUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * create time 2025/1/18 16:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityMigrationMetadata {
    private final EntityMetadata entityMetadata;
    private final Map<String, Field> allFieldsMap;
    private final Class<?> proxyClass;
    private final ValueHolder<Integer> fieldCommentErrorCount = new ValueHolder<>();

    public EntityMigrationMetadata(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
        this.allFieldsMap = EasyClassUtil.getAllFieldsMap(entityMetadata.getEntityClass());
        this.proxyClass = getProxyClass(entityMetadata);
        fieldCommentErrorCount.setValue(0);
    }

    private Class<?> getProxyClass(EntityMetadata entityMetadata) {
        String fullClassName = entityMetadata.getEntityClass().getCanonicalName();

        String defaultClassProxyName = getDefaultClassProxyName(fullClassName);
        try {
            return Class.forName(defaultClassProxyName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private String getDefaultClassProxyName(String fullClassName) {
        return fullClassName.substring(0, fullClassName.lastIndexOf(".")) + ".proxy." + fullClassName.substring(fullClassName.lastIndexOf(".") + 1) + "Proxy";
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public Field getFieldByName(ColumnMetadata columnMetadata) {
        return allFieldsMap.get(columnMetadata.getFieldName());
    }

    public String getFieldComment(String fieldName) {
        if (this.proxyClass != null) {
            if (fieldCommentErrorCount.getValue() > 1) {
                return null;
            }
            Object fieldComment = EasyStaticMethodUtil.invokeStaticMethod(this.proxyClass, "getFieldComment", new String[]{"java.lang.String"}, new Object[]{fieldName}, ex -> {
                fieldCommentErrorCount.setValue(fieldCommentErrorCount.getValue() + 1);
            });
            if (fieldComment != null) {
                byte[] decode = EasyBase64Util.decode(fieldComment.toString().getBytes(StandardCharsets.UTF_8));
                return new String(decode, StandardCharsets.UTF_8);
            }
        }
        return null;
    }
}
