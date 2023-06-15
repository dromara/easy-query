package com.easy.query.core.util;

import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityTrackProperty;
import com.easy.query.core.basic.extension.track.TrackDiffEntry;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.exception.EasyQueryTrackInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.TrackKeyFunc;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejiaming
 * @FileName: TrackUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/19 21:36
 */
public class EasyTrackUtil {
    private static final ConcurrentHashMap<Class<?>, TrackKeyFunc<Object>> trackKeyFuncMap = new ConcurrentHashMap<>();

    /**
     * 返回未null表示当前对象没有追踪key无法追踪
     * 属性名+冒号+属性值多个之间用逗号分割,除了key外额外添加分片字段
     *
     * @param entityMetadata
     * @param entity
     * @return userId:123,userName:xxxx
     */
    public static String getTrackKey(EntityMetadata entityMetadata, Object entity) {
        //构建获取对象追踪key表达式缓存
        TrackKeyFunc<Object> entityTrackKeyFunc =EasyMapUtil.computeIfAbsent(trackKeyFuncMap,entity.getClass(), k -> {
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            int shardingCapacity = 0;
            boolean multiDataSourceMapping = entityMetadata.isMultiDataSourceMapping();
            if (multiDataSourceMapping) {
                shardingCapacity++;
            }
            boolean multiTableMapping = entityMetadata.isMultiTableMapping();
            if (multiTableMapping) {
                shardingCapacity++;
            }
            LinkedHashMap<String, Property<Object, ?>> propertiesMap = new LinkedHashMap<>(keyProperties.size() + shardingCapacity);

            FastBean fastBean = EasyBeanUtil.getFastBean(entity.getClass());
            for (String keyProperty : keyProperties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(keyProperty);
                Property<Object, ?> lambdaProperty = fastBean.getBeanGetter(columnMetadata.getProperty());
                propertiesMap.put(keyProperty, lambdaProperty);
            }
            if (multiDataSourceMapping) {
                String propertyName = entityMetadata.getShardingDataSourcePropertyName();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                Property<Object, ?> lambdaProperty = fastBean.getBeanGetter(columnMetadata.getProperty());
                propertiesMap.put(propertyName, lambdaProperty);
            }
            if (multiTableMapping) {
                String propertyName = entityMetadata.getShardingTablePropertyName();
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                Property<Object, ?> lambdaProperty = fastBean.getBeanGetter(columnMetadata.getProperty());
                propertiesMap.put(propertyName, lambdaProperty);
            }
            return o -> {
                if (propertiesMap.isEmpty()) {
                    return null;
                }
                StringBuilder trackKey = new StringBuilder();
                Iterator<Map.Entry<String, Property<Object, ?>>> iterator = propertiesMap.entrySet().iterator();
                Map.Entry<String, Property<Object, ?>> firstProperty = iterator.next();
                String firstTrackValue = getAndCheckTrackValue(o, firstProperty);
                trackKey.append(firstTrackValue);
                while (iterator.hasNext()) {
                    Map.Entry<String, Property<Object, ?>> property = iterator.next();
                    String trackValue = getAndCheckTrackValue(o, property);
                    trackKey.append(",").append(trackValue);
                }
                return trackKey.toString();
            };
        });
        return entityTrackKeyFunc.get(entity);
    }

    private static String getAndCheckTrackValue(Object entity, Map.Entry<String, Property<Object, ?>> property) {

        String propertyName = property.getKey();
        Object propertyValue = property.getValue().apply(entity);
        if (Objects.isNull(propertyValue)) {
            throw new EasyQueryTrackInvalidOperationException("track key cant null :" + propertyName);
        }
        return propertyName + ":" + propertyValue;
    }

    /**
     * 获取追踪不一样的属性
     *
     * @param entityMetadataManager
     * @param entityState
     * @return
     */

    public static EntityTrackProperty getTrackDiffProperty(EntityMetadataManager entityMetadataManager, EntityState entityState) {
        Class<?> entityClass = entityState.getEntityClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        EntityTrackProperty entityTrackProperty = new EntityTrackProperty();
        Collection<String> properties = entityMetadata.getProperties();
        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            if (columnMetadata.isPrimary()) {
                continue;
            }
            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(columnMetadata.getProperty());

            Object originalPropertyValue = propertyGetter.apply(entityState.getOriginalValue());
            Object currentPropertyValue = propertyGetter.apply(entityState.getCurrentValue());

            if ((originalPropertyValue == null && currentPropertyValue == null) || Objects.equals(originalPropertyValue, currentPropertyValue)) {
                entityTrackProperty.getSameProperties().add(propertyName);
            } else {
                entityTrackProperty.getDiffProperties().put(propertyName, new TrackDiffEntry(originalPropertyValue, currentPropertyValue));
            }
        }
        return entityTrackProperty;
    }

//    public static Set<String> getTrackIgnoreProperties(EntityMetadataManager entityMetadataManager, EntityState entityState) {
//        Class<?> entityClass = entityState.getEntityClass();
//        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
//
//        Set<String> ignoreSets = new HashSet<>();
//        Collection<String> properties = entityMetadata.getProperties();
//        FastBean fastBean = EasyBeanUtil.getFastBean(entityClass);
//        for (String propertyName : properties) {
//            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
//            if (columnMetadata.isPrimary()) {
//                continue;
//            }
//            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(columnMetadata.getProperty());
//
//            Object originalPropertyValue = propertyGetter.apply(entityState.getOriginalValue());
//            Object currentPropertyValue = propertyGetter.apply(entityState.getCurrentValue());
//            if ((originalPropertyValue == null && currentPropertyValue == null) || Objects.equals(originalPropertyValue, currentPropertyValue)) {
//                ignoreSets.add(propertyName);
//            }
//        }
//        return ignoreSets;
//    }
}
