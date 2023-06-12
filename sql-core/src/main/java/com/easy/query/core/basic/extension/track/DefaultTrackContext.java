package com.easy.query.core.basic.extension.track;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.bean.BeanCaller;
import com.easy.query.core.bean.BeanValueCaller;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejiaming
 * @FileName: DefaultTrackContext.java
 * @Description: 文件说明
 * @Date: 2023/3/19 21:13
 */
public class DefaultTrackContext implements TrackContext {
    private final EntityMetadataManager entityMetadataManager;
    private final BeanValueCaller beanValueCaller;
    private int beginCount = 0;
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, EntityState>> trackEntityMap = new ConcurrentHashMap<>();

    public DefaultTrackContext(EntityMetadataManager entityMetadataManager, BeanValueCaller beanValueCaller) {
        this.entityMetadataManager = entityMetadataManager;
        this.beanValueCaller = beanValueCaller;
    }

    @Override
    public void begin() {
        beginCount++;
    }

    @Override
    public boolean isTrack(Object entity) {
        return getTrackEntityState(entity) != null;
    }

    @Override
    public EntityState getTrackEntityState(Object entity) {
        if (entity == null) {
            return null;
        }
        ConcurrentHashMap<String, EntityState> entityStateTrackMap = trackEntityMap.get(entity.getClass());
        if (entityStateTrackMap == null || entityStateTrackMap.isEmpty()) {
            return null;
        }
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
        String trackKey = EasyTrackUtil.getTrackKey(beanValueCaller,entityMetadata, entity);
        if (trackKey == null) {
            return null;
        }
        return entityStateTrackMap.get(trackKey);
    }

    @Override
    public boolean addTracking(Object entity) {
        EntityState entityState = addInternalTracking(entity, false);
        return entityState.getCurrentValue() == entity;
    }

    @Override
    public EntityState addQueryTracking(Object entity) {
        return addInternalTracking(entity, true);
    }

    private EntityState addInternalTracking(Object entity, boolean isQuery) {
        if (entity == null) {
            throw new EasyQueryException("cant track null entity");
        }

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": is not table entity,not allowed track");
        }
        String trackKey = EasyTrackUtil.getTrackKey(beanValueCaller,entityMetadata, entity);
        if (trackKey == null) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        ConcurrentHashMap<String, EntityState> entityStateMap = trackEntityMap.computeIfAbsent(entityClass, o -> new ConcurrentHashMap<>());
        EntityState originalEntityState = entityStateMap.get(trackKey);
        if (originalEntityState != null) {
            //查询的情况下直接使用追踪后的数据
            if (!isQuery && originalEntityState.getCurrentValue() != entity) {//内存地址不一样就说明追踪的不是同一个对象那么将要报错
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity already tracked key,primary key:" + trackKey);
            }
            return originalEntityState;
        } else {
            Object original = createAndCopyValue(entity, entityMetadata);
            EntityState entityState = new EntityState(entityClass, trackKey, original, entity);
            entityStateMap.putIfAbsent(trackKey, entityState);
            return entityState;
        }
    }

    @Override
    public boolean removeTracking(Object entity) {

        if (entity == null) {
            throw new EasyQueryException("cant track null entity");
        }

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": is not table entity,cant tracking");
        }
        String trackKey = EasyTrackUtil.getTrackKey(beanValueCaller,entityMetadata, entity);
        if (trackKey == null) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        ConcurrentHashMap<String, EntityState> entityStateMap = trackEntityMap.computeIfAbsent(entityClass, o -> new ConcurrentHashMap<>());

        return entityStateMap.remove(trackKey) != null;
    }

    /**
     * 实体对象零拷贝
     *
     * @param entity
     * @param entityMetadata
     * @return
     */
    private Object createAndCopyValue(Object entity, EntityMetadata entityMetadata) {

        Object original = EasyClassUtil.newInstance(entity.getClass());
        BeanCaller beanCaller = beanValueCaller.getBeanCaller(entity.getClass());
        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {
            ColumnMetadata columnMetadata = columnMetadataEntry.getValue();
            PropertyDescriptor property = columnMetadata.getProperty();
            PropertySetterCaller<Object> beanSetter = beanCaller.getBeanSetter(property);
            Class<?> propertyType = columnMetadata.getPropertyType();
            Property<Object, ?> beanGetter = beanCaller.getBeanGetter(property);
            Object value = beanGetter.apply(entity);
            if (EasyClassUtil.isBasicType(propertyType) || EasyClassUtil.isEnumType(propertyType)) {

                beanSetter.call(original, value);
            } else {
                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
                Object serializeValue = valueConverter.serialize(EasyObjectUtil.typeCastNullable(value));
                Object deserialize = valueConverter.deserialize(EasyObjectUtil.typeCastNullable(propertyType), EasyObjectUtil.typeCastNullable(serializeValue));
                beanSetter.call(original, deserialize);
            }
        }
        return original;
    }

    @Override
    public void release() {
        if (beginCount > 0) {
            beginCount--;
        }
        if (beginCount == 0) {
            trackEntityMap.clear();
        }

    }

}
