package com.easy.query.core.util;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.TrackKeyFunc;
import com.easy.query.core.basic.plugin.track.EntityState;
import com.easy.query.core.basic.plugin.track.TrackDiffEntry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: TrackUtil.java
 * @Description: 文件说明
 * @Date: 2023/3/19 21:36
 * @author xuejiaming
 */
public class TrackUtil {
    private static final ConcurrentHashMap<Class<?>, TrackKeyFunc<Object>> trackKeyFuncMap = new ConcurrentHashMap<>();

    /**
     * 返回未null表示当前对象没有追踪key无法追踪
     *
     * @param entityMetadataManager
     * @param entity
     * @return
     */
    public static String getTrackKey(EntityMetadataManager entityMetadataManager, Object entity) {
        //构建获取对象追踪key表达式缓存
        TrackKeyFunc<Object> entityTrackKeyFunc = trackKeyFuncMap.computeIfAbsent(entity.getClass(), k -> {
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
            Collection<String> keyProperties = entityMetadata.getKeyProperties();

            ArrayList<Property<Object, ?>> properties = new ArrayList<>(keyProperties.size());
            FastBean fastBean = EasyUtil.getFastBean(entity.getClass());
            for (String keyProperty : keyProperties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(keyProperty);
                Property<Object, ?> lambdaProperty = fastBean.getBeanGetter(columnMetadata.getProperty());
                properties.add(lambdaProperty);
            }
            return o -> {
                if (EasyCollectionUtil.isEmpty(properties)) {
                    return null;
                }
                StringBuilder trackKey = new StringBuilder();

                Iterator<Property<Object, ?>> iterator = properties.iterator();
                Property<Object, ?> firstProperty = iterator.next();
                trackKey.append(firstProperty.apply(o));
                while (iterator.hasNext()) {
                    trackKey.append(",").append(iterator.next().apply(o));
                }
                return trackKey.toString();
            };
        });
        return entityTrackKeyFunc.get(entity);
    }

    /**
     * 获取追踪不一样的属性
     * @param entityMetadataManager
     * @param entityState
     * @return
     */

    public static Map<String, TrackDiffEntry> getTrackDiffProperty(EntityMetadataManager entityMetadataManager, EntityState entityState) {
        Class<?> entityClass = entityState.getEntityClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Map<String, TrackDiffEntry> diffProperties =new LinkedHashMap<>();
        Collection<String> properties = entityMetadata.getProperties();
        FastBean fastBean = EasyUtil.getFastBean(entityClass);
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(propertyName, columnMetadata.getProperty().getPropertyType());

            Object originalPropertyValue = propertyGetter.apply(entityState.getOriginalValue());
            Object currentPropertyValue = propertyGetter.apply(entityState.getCurrentValue());
            if (
                    (originalPropertyValue == null && currentPropertyValue != null)
                            ||
                            !Objects.equals(originalPropertyValue, currentPropertyValue)
            ) {
                if (entityMetadata.isKeyProperty(propertyName)) {
                    throw new EasyQueryException(ClassUtil.getSimpleName(entityClass) + ": track entity cant modify primary key:[" + originalPropertyValue + "," + currentPropertyValue + "]");
                }
                diffProperties.put(propertyName,new TrackDiffEntry(originalPropertyValue,currentPropertyValue));
            }
        }
        return diffProperties;
    }
    public static Set<String> getTrackIgnoreProperties(EntityMetadataManager entityMetadataManager, EntityState entityState) {
        Class<?> entityClass = entityState.getEntityClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);

        Set<String> ignoreSets = new HashSet<>();
        Collection<String> properties = entityMetadata.getProperties();
        FastBean fastBean = EasyUtil.getFastBean(entityClass);
        for (String propertyName : properties) {
            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
            Property<Object, ?> propertyGetter = fastBean.getBeanGetter(propertyName, columnMetadata.getProperty().getPropertyType());

            Object originalPropertyValue = propertyGetter.apply(entityState.getOriginalValue());
            Object currentPropertyValue = propertyGetter.apply(entityState.getCurrentValue());
            if (!Objects.equals(originalPropertyValue, currentPropertyValue)) {
                if (entityMetadata.isKeyProperty(propertyName)) {
                    throw new EasyQueryException(ClassUtil.getSimpleName(entityClass) + ": track entity cant modify primary key:[" + originalPropertyValue + "," + currentPropertyValue + "]");
                }
            }else{
                ignoreSets.add(propertyName);
            }
        }
        return ignoreSets;
    }
}
