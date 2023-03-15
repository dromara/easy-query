package com.easy.query.core.abstraction.metadata;

import com.easy.query.core.annotation.*;
import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.interceptor.GlobalEntityInterceptor;
import com.easy.query.core.interceptor.GlobalInterceptor;
import com.easy.query.core.interceptor.GlobalPredicateFilterInterceptor;
import com.easy.query.core.interceptor.GlobalUpdateSetInterceptor;
import com.easy.query.core.logicdel.GlobalLogicDeleteStrategy;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.common.LinkedCaseInsensitiveMap;
import com.easy.query.core.util.ClassUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

/**
 * @FileName: EntityMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:17
 * @Created by xuejiaming
 */
public class EntityMetadata {
    private final Class<?> entityClass;
    private String tableName;


    private LogicDeleteMetadata logicDeleteMetadata;
    /**
     * 查询过滤器
     */
    private final List<String> predicateFilterInterceptors = new ArrayList<>();
    private final List<String> entityInterceptors = new ArrayList<>();
    private final List<String> updateSetInterceptors = new ArrayList<>();
    private final LinkedHashMap<String, ColumnMetadata> property2ColumnMap = new LinkedHashMap<>();
    private final Map<String/*property name*/, String/*column name*/> keyPropertiesMap = new HashMap<>();
    private final LinkedCaseInsensitiveMap<String> column2PropertyMap = new LinkedCaseInsensitiveMap<>(Locale.ENGLISH);

    public EntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void init(EasyQueryConfiguration jdqcConfiguration) {
        classInit(jdqcConfiguration);
        propertyInit(jdqcConfiguration);
        entityGlobalQueryFilterConfigurationInit(jdqcConfiguration);
    }

    protected void classInit(EasyQueryConfiguration configuration) {

        NameConversion nameConversion = configuration.getNameConversion();
        this.tableName = nameConversion.getTableName(entityClass);
    }

    private PropertyDescriptor firstOrNull(PropertyDescriptor[] ps, Predicate<PropertyDescriptor> predicate) {
        for (PropertyDescriptor p : ps) {
            if (predicate.test(p)) {
                return p;
            }
        }
        return null;
    }

    protected void propertyInit(EasyQueryConfiguration configuration) {
        NameConversion nameConversion = configuration.getNameConversion();
        List<Field> allFields = ClassUtil.getAllFields(this.entityClass);
        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (Field field : allFields) {
            String property = field.getName();
            //未找到bean属性就直接忽略
            PropertyDescriptor propertyDescriptor = firstOrNull(ps, o -> Objects.equals(o.getName(), property));
            if(propertyDescriptor==null){
                continue;
            }
            ColumnIgnore columnIgnore = field.getAnnotation(ColumnIgnore.class);
            if (columnIgnore != null) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);
            boolean hasColumnName = column != null && !StringUtil.isBlank(column.value());
            String columnName = hasColumnName ? column.value() : nameConversion.getColName(property);
            ColumnMetadata columnMetadata = new ColumnMetadata(this, columnName);
//            if (column != null) {
//                columnMetadata.setNullable(column.nullable());
//            }
            columnMetadata.setProperty(propertyDescriptor);
            property2ColumnMap.put(property, columnMetadata);
            column2PropertyMap.put(columnName, property);

            if (StringUtil.isNotBlank(tableName)) {

                PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
                if (primaryKey != null) {
                    columnMetadata.setPrimary(true);
                    columnMetadata.setIncrement(primaryKey.increment());
//                    columnMetadata.setNullable(false);//如果为主键那么之前设置的nullable将无效
                    keyPropertiesMap.put(property, columnName);
                }
                InsertIgnore insertIgnore = field.getAnnotation(InsertIgnore.class);
                if (insertIgnore != null) {
                    columnMetadata.setInsertIgnore(true);
                }

                UpdateIgnore updateIgnore = field.getAnnotation(UpdateIgnore.class);
                if (updateIgnore != null) {
                    columnMetadata.setUpdateIgnore(true);
                }
                Version version = field.getAnnotation(Version.class);
                if (version != null) {
                    columnMetadata.setVersion(true);
                }
                LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
                if (logicDelete != null) {
                    LogicDeleteStrategyEnum strategy = logicDelete.strategy();
                    if (Objects.equals(LogicDeleteStrategyEnum.CUSTOM, strategy)) {//使用自定义
                        String strategyName = logicDelete.strategyName();
                        if (StringUtil.isNotBlank(strategyName)) {
                            GlobalLogicDeleteStrategy globalLogicDeleteStrategy = configuration.getGlobalLogicDeleteStrategy(strategyName);
                            if (globalLogicDeleteStrategy != null) {
                                globalLogicDeleteStrategy.configure(this, property, field.getType());
                            }
                        }
                    } else {//使用系统默认的
                        GlobalLogicDeleteStrategy sysGlobalLogicDeleteStrategy = configuration.getSysGlobalLogicDeleteStrategy(strategy);
                        if (sysGlobalLogicDeleteStrategy != null) {
                            sysGlobalLogicDeleteStrategy.configure(this, property, field.getType());
                        }
                    }
                }
            }
        }
    }

    protected void entityGlobalQueryFilterConfigurationInit(EasyQueryConfiguration configuration) {

        if (StringUtil.isNotBlank(tableName)) {

            Collection<GlobalInterceptor> globalInterceptors = configuration.getGlobalInterceptors();
            for (GlobalInterceptor globalInterceptor : globalInterceptors) {
                if (globalInterceptor.apply(entityClass)) {
                    if (globalInterceptor instanceof GlobalPredicateFilterInterceptor) {
                        predicateFilterInterceptors.add(globalInterceptor.name());
                    }
                    if (globalInterceptor instanceof GlobalEntityInterceptor) {
                        entityInterceptors.add(globalInterceptor.name());
                    }
                    if (globalInterceptor instanceof GlobalUpdateSetInterceptor) {
                        updateSetInterceptors.add(globalInterceptor.name());
                    }
                }
            }
        }
    }

    private PropertyDescriptor[] getPropertyDescriptor() {
        try {
            return ClassUtil.propertyDescriptors(entityClass);
        } catch (IntrospectionException e) {
            throw new EasyQueryException(e);
        }
    }


    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName(String attrName) {
        ColumnMetadata columnMetadata = property2ColumnMap.get(attrName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("未找到属性:[%s]对应的列名", attrName));
        }
        return columnMetadata.getName();
    }

    public String getPropertyName(String columnName) {
        return getPropertyName(columnName, columnName);
    }

    public String getPropertyName(String columnName, String def) {
        String propertyName = column2PropertyMap.get(columnName);
        if (propertyName == null) {
            return def;
        }
        return propertyName;
    }

    public Collection<ColumnMetadata> getColumns() {
        return property2ColumnMap.values();
    }

    public Collection<String> getProperties() {
        return property2ColumnMap.keySet();
    }

    public LinkedHashMap<String, ColumnMetadata> getProperty2ColumnMap() {
        return property2ColumnMap;
    }

    /**
     * 获取所有的key
     *
     * @return
     */
    public Collection<String> getKeyProperties() {
        return keyPropertiesMap.keySet();
    }

    public ColumnMetadata getColumnNotNull(String propertyName) {
        ColumnMetadata columnMetadata = getColumnOrNull(propertyName);
        if (columnMetadata == null) {
            throw new EasyQueryException(String.format("未找到属性:[%s]对应的列名", propertyName));
        }
        return columnMetadata;
    }

    public ColumnMetadata getColumnOrNull(String propertyName) {
        return property2ColumnMap.get(propertyName);
    }

    public void checkTable() {
        if (StringUtil.isEmpty(tableName)) {
            throw new EasyQueryException("当前对象不是数据库对象," + ClassUtil.getSimpleName(entityClass));
        }
    }

    public void setLogicDeleteMetadata(LogicDeleteMetadata logicDeleteMetadata) {
        this.logicDeleteMetadata = logicDeleteMetadata;
    }

    public LogicDeleteMetadata getLogicDeleteMetadata() {
        return logicDeleteMetadata;
    }

    /**
     * 是否启用逻辑删除
     *
     * @return
     */
    public boolean enableLogicDelete() {
        return logicDeleteMetadata != null;
    }


    public List<String> getPredicateFilterInterceptors() {
        return predicateFilterInterceptors;
    }

    public List<String> getEntityInterceptors() {
        return entityInterceptors;
    }

    public List<String> getUpdateSetInterceptors() {
        return updateSetInterceptors;
    }

}
