package org.easy.query.core.abstraction.metadata;

import org.easy.query.core.config.EasyQueryConfiguration;
import org.easy.query.core.config.NameConversion;
import org.easy.query.core.exception.JDQCException;
import org.easy.query.core.util.StringUtil;
import org.easy.query.core.annotation.*;
import org.easy.query.core.common.LinkedCaseInsensitiveMap;
import org.easy.query.core.util.ClassUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
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
    private  String tableName;

    private final LinkedHashMap<String,ColumnMetadata> property2ColumnMap=new LinkedHashMap<>();
    private final Map<String/*property name*/,String/*column name*/> keyPropertiesMap=new HashMap<>();
    private final LinkedCaseInsensitiveMap<String> column2PropertyMap=new LinkedCaseInsensitiveMap<>(Locale.ENGLISH);

    public EntityMetadata(Class entityClass) {
        this.entityClass = entityClass;
    }

    public void init(EasyQueryConfiguration jdqcConfiguration){
        classInit(jdqcConfiguration);
        propertyInit(jdqcConfiguration);
    }
    protected void classInit(EasyQueryConfiguration jdqcConfiguration){

        NameConversion nameConversion = jdqcConfiguration.getNameConversion();
        this.tableName = nameConversion.getTableName(entityClass);
    }
    protected void propertyInit(EasyQueryConfiguration jdqcConfiguration){
        NameConversion nameConversion = jdqcConfiguration.getNameConversion();
        List<Field> allFields = ClassUtil.getAllFields(this.entityClass);
        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (Field field : allFields) {
            String attr = field.getName();
            PropertyDescriptor propertyDescriptor = Arrays.stream(ps).filter(o -> Objects.equals(o.getName(), attr)).findFirst().orElse(null);
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
            columnMetadata.setProperty(propertyDescriptor);
            property2ColumnMap.put(attr,columnMetadata);
            column2PropertyMap.put(columnName,attr);

            PrimaryKey primaryKey =  field.getAnnotation(PrimaryKey.class);
            if(primaryKey!=null){
                columnMetadata.setPrimary(true);
                columnMetadata.setIncrement(primaryKey.increment());
                keyPropertiesMap.put(attr,columnName);
            }

            InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
            if(insertIgnore!=null){
                columnMetadata.setInsertIgnore(true);
            }

            UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
            if(updateIgnore!=null){
                columnMetadata.setUpdateIgnore(true);
            }
            Version version = field.getAnnotation(Version.class);
            if(version!=null){
                columnMetadata.setVersion(true);
            }
        }
    }

    private PropertyDescriptor[] getPropertyDescriptor() {
        try {
            return ClassUtil.propertyDescriptors(entityClass);
        } catch (IntrospectionException e) {
            throw new JDQCException(e);
        }
    }


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

    public String getPropertyName(String columnName){
        return getPropertyName(columnName,columnName);
    }
    public String getPropertyName(String columnName,String def){
        String propertyName = column2PropertyMap.get(columnName);
        if(propertyName==null){
            return def;
        }
        return propertyName;
    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }
    public LinkedHashMap<String,ColumnMetadata> getProperty2ColumnMap() {
        return property2ColumnMap;
    }

    /**
     * 获取所有的key
     * @return
     */
    public Collection<String> getKeyProperties() {
        return keyPropertiesMap.keySet();
    }
    public ColumnMetadata getColumn(String propertyName) {
        ColumnMetadata columnMetadata = property2ColumnMap.get(propertyName);
        if(columnMetadata==null){
            throw new JDQCException(String.format("未找到属性:[%s]对应的列名",propertyName));
        }
        return columnMetadata;
    }
    public void checkTable(){
        if(StringUtil.isEmpty(tableName)){
            throw new JDQCException("当前对象不是数据库对象,"+entityClass.getSimpleName());
        }
    }
}
