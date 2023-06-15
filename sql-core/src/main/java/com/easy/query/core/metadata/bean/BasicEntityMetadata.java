package com.easy.query.core.metadata.bean;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ColumnIgnore;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.InsertIgnore;
import com.easy.query.core.annotation.LogicDelete;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraDataSourceKey;
import com.easy.query.core.annotation.ShardingExtraTableKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.annotation.UpdateIgnore;
import com.easy.query.core.annotation.Version;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * create time 2023/6/15 10:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicEntityMetadata {
    private final Class<?> entityClass;
    private final Table tableAnnotation;

    private final Map<String,PropertyMetadata> properties=new LinkedHashMap<>();
    public BasicEntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.tableAnnotation = EasyClassUtil.getAnnotation(entityClass, Table.class);

        HashSet<String> ignoreProperties = tableAnnotation != null ? new HashSet<>(Arrays.asList(tableAnnotation.ignoreProperties())) : new HashSet<>();

        List<Field> allFields = EasyClassUtil.getAllFields(this.entityClass);
        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (Field field : allFields) {
            String property = field.getName();
            if (ignoreProperties.contains(property)) {
                continue;
            }
            //未找到bean属性就直接忽略
            PropertyDescriptor propertyDescriptor = firstOrNull(ps, o -> Objects.equals(o.getName(), property));
            if (propertyDescriptor == null) {
                continue;
            }
            PropertyMetadata propertyMetadata = new PropertyMetadata(propertyDescriptor);
            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if (columnIgnore != null) {
                propertyMetadata.setColumnIgnoreAnnotation(columnIgnore);
                properties.put(property,propertyMetadata);
                continue;
            }

            Column column = field.getAnnotation(Column.class);
            propertyMetadata.setColumnAnnotation(column);

            Encryption encryption = field.getAnnotation(Encryption.class);
            propertyMetadata.setEncryptionAnnotation(encryption);
            if(tableAnnotation!=null&&EasyStringUtil.isNotBlank(tableAnnotation.value())){

                InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
                propertyMetadata.setInsertIgnoreAnnotation(insertIgnore);

                UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
                propertyMetadata.setUpdateIgnoreAnnotation(updateIgnore);

                Version version = field.getAnnotation(Version.class);
                propertyMetadata.setVersionAnnotation(version);

                ShardingDataSourceKey shardingDataSourceKey = field.getAnnotation(ShardingDataSourceKey.class);
                propertyMetadata.setShardingDataSourceKeyAnnotation(shardingDataSourceKey);

                ShardingExtraDataSourceKey shardingExtraDataSourceKey = field.getAnnotation(ShardingExtraDataSourceKey.class);
                propertyMetadata.setShardingExtraDataSourceKeyAnnotation(shardingExtraDataSourceKey);

                ShardingTableKey shardingTableKey = field.getAnnotation(ShardingTableKey.class);
                propertyMetadata.setShardingTableKeyAnnotation(shardingTableKey);

                ShardingExtraTableKey shardingExtraTableKey = field.getAnnotation(ShardingExtraTableKey.class);
                propertyMetadata.setShardingExtraTableKeyAnnotation(shardingExtraTableKey);

                LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
                propertyMetadata.setLogicDeleteAnnotation(logicDelete);
            }
            properties.put(property,propertyMetadata);
        }
    }


    private PropertyDescriptor firstOrNull(PropertyDescriptor[] ps, Predicate<PropertyDescriptor> predicate) {
        for (PropertyDescriptor p : ps) {
            if (predicate.test(p)) {
                return p;
            }
        }
        return null;
    }

    private PropertyDescriptor[] getPropertyDescriptor() {
        try {
            return EasyClassUtil.propertyDescriptors(entityClass);
        } catch (IntrospectionException e) {
            throw new EasyQueryException(e);
        }
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Table getTableAnnotation() {
        return tableAnnotation;
    }

    public Map<String, PropertyMetadata> getProperties() {
        return properties;
    }
}
