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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @FileName: ClassMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:17
 * @Created by xuejiaming
 */
public class EntityMetadata {
    private final Class entityClass;
    private final String tableName;

    private final LinkedHashMap<String,ColumnMetadata> property2ColumnMap=new LinkedHashMap<>();
    private final LinkedCaseInsensitiveMap<String> column2PropertyMap=new LinkedCaseInsensitiveMap<>(Locale.ENGLISH);

    public EntityMetadata(Class entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
    }

    public void init(JDQCConfiguration jdqcConfiguration){
        propertyInit(jdqcConfiguration);
    }
    protected void propertyInit(JDQCConfiguration jdqcConfiguration){
        NameConversion nameConversion = jdqcConfiguration.getNameConversion();
        List<Field> allFields = ClassUtil.getAllFields(this.entityClass);
//        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (Field field : allFields) {
            String attr = field.getName();
            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if(columnIgnore!=null){
                continue;
            }

            Column column = field.getAnnotation(Column.class);
            boolean hasColumnName = column != null && !StringUtil.isBlank(column.value());
            String columnName=hasColumnName?column.value():nameConversion.getColName(attr);
            ColumnMetadata columnMetadata = new ColumnMetadata(this, columnName);
            if(column!=null){
                columnMetadata.setNullable(column.nullable());
            }
            property2ColumnMap.put(attr,columnMetadata);
            column2PropertyMap.put(columnName,attr);

            PrimaryKey primaryKey =  field.getAnnotation(PrimaryKey.class);
            if(primaryKey!=null){
                columnMetadata.setPrimary(true);
                columnMetadata.setIncrement(primaryKey.increment());
            }

            InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
            if(insertIgnore!=null){
                columnMetadata.setInsertIgnore(true);
            }

            UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
            if(updateIgnore!=null){
                columnMetadata.setUdpateIgnore(true);
            }
            Version version = field.getAnnotation(Version.class);
            if(version!=null){
                columnMetadata.setVersion(true);
            }
        }
    }

//    private PropertyDescriptor[] getPropertyDescriptor() {
//        try {
//            return ClassUtil.propertyDescriptors(entityClass);
//        } catch (IntrospectionException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public Class getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName(String attrName){
        ColumnMetadata columnMetadata = property2ColumnMap.get(attrName);
        if(columnMetadata==null){
            throw new JDQCException(String.format("未找到属性:[%s]对应的列名",attrName));
        }
        return columnMetadata.getName();
    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }
}
