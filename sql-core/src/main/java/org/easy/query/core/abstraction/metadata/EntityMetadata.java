package org.easy.query.core.abstraction.metadata;

import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.core.config.NameConversion;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.util.StringUtil;
import org.easy.query.core.annotation.*;
import org.easy.query.core.common.LinkedCaseInsensitiveMap;
import org.easy.query.core.util.ClassUtil;

import java.lang.reflect.Field;
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

    public void init(EasyQueryConfiguration jdqcConfiguration){
        propertyInit(jdqcConfiguration);
    }
    protected void propertyInit(EasyQueryConfiguration jdqcConfiguration){
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
