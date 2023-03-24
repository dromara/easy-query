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
import java.util.stream.Collectors;

/**
 * @FileName: EntityMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 10:17
 * @author xuejiaming
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
    private final Map<String/*property name*/, String/*column name*/> keyPropertiesMap = new LinkedHashMap<>();
    private final List<String/*column name*/> incrementColumns = new ArrayList<>(4);
    private final LinkedCaseInsensitiveMap<String> column2PropertyMap = new LinkedCaseInsensitiveMap<>(Locale.ENGLISH);

    public EntityMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public void init(EasyQueryConfiguration jdqcConfiguration) {
//        classInit(jdqcConfiguration);
        propertyInit(jdqcConfiguration);
        entityGlobalInterceptorConfigurationInit(jdqcConfiguration);
    }

//    protected void classInit(EasyQueryConfiguration configuration) {
//        NameConversion nameConversion = configuration.getNameConversion();
//        this.tableName = nameConversion.getTableName(entityClass);
//    }

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

        Table table = ClassUtil.getAnnotation(entityClass, Table.class);
        this.tableName = (table == null || StringUtil.isBlank(table.value())) ? nameConversion.convert(ClassUtil.getSimpleName(entityClass)) : table.value();

        HashSet<String> ignoreProperties =table!=null?new HashSet<>(Arrays.asList(table.ignoreProperties())) :new HashSet<>();

        List<Field> allFields = ClassUtil.getAllFields(this.entityClass);
        PropertyDescriptor[] ps = getPropertyDescriptor();
        for (Field field : allFields) {
            String property = field.getName();
            if(ignoreProperties.contains(property)){
                continue;
            }
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
            boolean hasColumnName = column != null && StringUtil.isNotBlank(column.value());
            String columnName = hasColumnName ? column.value() : nameConversion.convert(property);
            ColumnMetadata columnMetadata = new ColumnMetadata(this, columnName);
//            if (column != null) {
//                columnMetadata.setNullable(column.nullable());
//            }
            columnMetadata.setProperty(propertyDescriptor);
            property2ColumnMap.put(property, columnMetadata);
            column2PropertyMap.put(columnName, property);

            if (StringUtil.isNotBlank(tableName)) {

                if(column!=null){
                    if(column.primaryKey()){
                        keyPropertiesMap.put(property, columnName);
                    }
                    columnMetadata.setPrimary(column.primaryKey());
                    if(column.increment()){
                        incrementColumns.add(columnName);
                    }
                    columnMetadata.setIncrement(column.increment());

//                    columnMetadata.setSelect(column.select());
//                    columnMetadata.setNullable(false);//如果为主键那么之前设置的nullable将无效
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

    protected void entityGlobalInterceptorConfigurationInit(EasyQueryConfiguration configuration) {

        if (StringUtil.isNotBlank(tableName)) {

            List<GlobalInterceptor> globalInterceptors = configuration.getGlobalInterceptors().stream().sorted(Comparator.comparingInt(GlobalInterceptor::order)).collect(Collectors.toList());
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

    /**
     * 忽略大小写
     * @param columnName
     * @param def
     * @return
     */
    public String getPropertyName(String columnName, String def) {
        String propertyName = column2PropertyMap.get(columnName);
        if (propertyName == null) {
            return def;
        }
        return propertyName;
    }
    public boolean containsColumnName(String columnName){
        return column2PropertyMap.containsKey(columnName);
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

    public boolean isKeyProperty(String propertyName){
        if(propertyName==null){
            return false;
        }
        return keyPropertiesMap.containsKey(propertyName);
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


    public List<String> getIncrementColumns() {
        return incrementColumns;
    }

}
