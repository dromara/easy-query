package org.jdqc.core.abstraction.metadata;

import org.jdqc.core.annotation.*;
import org.jdqc.core.common.LinkedCaseInsensitiveMap;
import org.jdqc.core.config.JDQCConfiguration;
import org.jdqc.core.config.NameConversion;
import org.jdqc.core.exception.JDQCException;
import org.jdqc.core.util.ClassUtil;
import org.jdqc.core.util.StringUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

/**
 * @FileName: ClassMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:17
 * @Created by xuejiaming
 */
public class EntityMetadata {
    private final Class entityClass;
    private final String tableName;

    private final HashMap<String, ColumnMetadata> property2ColumnMap = new HashMap<>();
    private final LinkedCaseInsensitiveMap<String> column2PropertyMap = new LinkedCaseInsensitiveMap<>(Locale.ENGLISH);

    public EntityMetadata(Class entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
    }

    public void init(JDQCConfiguration jdqcConfiguration) {
        propertyInit(jdqcConfiguration);
    }

    protected void propertyInit(JDQCConfiguration jdqcConfiguration) {
        NameConversion nameConversion = jdqcConfiguration.getNameConversion();
        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (PropertyDescriptor p : ps) {
            Method readMethod = p.getReadMethod();
            Class type = p.getPropertyType();
            String attr = p.getName();

            Column column = ClassUtil.getAnnotation(this.entityClass, attr, readMethod, Column.class);
            if (column == null) {
                continue;
            }
            boolean hasColumnName = !StringUtil.isBlank(column.value());
            String columnName = hasColumnName ? nameConversion.getColName(column.value()) : nameConversion.getColName(attr);
            ColumnMetadata columnMetadata = new ColumnMetadata(this, columnName);
            columnMetadata.setNullable(column.nullable());
            property2ColumnMap.put(attr, columnMetadata);
            column2PropertyMap.put(columnName, attr);

            PrimaryKey primaryKey = ClassUtil.getAnnotation(this.entityClass, attr, readMethod, PrimaryKey.class);
            if (primaryKey != null) {
                columnMetadata.setPrimary(true);
                columnMetadata.setIncrement(primaryKey.increment());
            }

            InsertIgnore insertIgnore = ClassUtil.getAnnotation(this.entityClass, attr, readMethod, InsertIgnore.class);
            if (insertIgnore != null) {
                columnMetadata.setInsertIgnore(true);
            }

            UpdateIgnore updateIgnore = ClassUtil.getAnnotation(this.entityClass, attr, readMethod, UpdateIgnore.class);
            if (updateIgnore != null) {
                columnMetadata.setUdpateIgnore(true);
            }
            Version version = ClassUtil.getAnnotation(this.entityClass, attr, readMethod, Version.class);
            if (version != null) {
                columnMetadata.setVersion(true);
            }
        }
    }

    private PropertyDescriptor[] getPropertyDescriptor() {
        try {
            return ClassUtil.propertyDescriptors(entityClass);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }


    public Class getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName(String attrName) {
        ColumnMetadata columnMetadata = property2ColumnMap.get(attrName);
        if (columnMetadata == null) {
            throw new JDQCException(String.format("未找到属性:[%s]对应的列名", attrName));
        }
        return columnMetadata.getName();
    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }
}
